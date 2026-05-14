package com.avaliacaoReinaldo.n2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ValidacoesIntegracaoTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    // TESTE 1: Validação de Preço Negativo
    @Test
    public void deveRetornarStatus409AoTentarCadastrarProdutoComPrecoNegativo() {
        String payloadProdutoInvalido = "{\n" +
                "  \"nome\": \"Placa Mãe B550\",\n" +
                "  \"preco\": -100.00,\n" +
                "  \"categoria\": \"PLACA_MAE\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payloadProdutoInvalido)
                .when()
                .post("/produtos")
                .then()
                .statusCode(409)
                .body(equalTo("O preço do componente deve ser maior que zero."));
    }

    // TESTE 2: Validação de CPF Inválido
    @Test
    public void deveRetornarStatus409AoTentarCadastrarUsuarioComCpfIncompleto() {
        String payloadUsuarioInvalido = "{\n" +
                "  \"nome\": \"Théo da Silva Sá\",\n" +
                "  \"cpf\": \"123\",\n" +
                "  \"email\": \"theo@email.com\",\n" +
                "  \"senha\": \"senha123\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(payloadUsuarioInvalido)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(409)
                .body(equalTo("O CPF deve conter exatamente 11 dígitos."));
    }
}