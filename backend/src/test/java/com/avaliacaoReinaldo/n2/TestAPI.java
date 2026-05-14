package com.avaliacaoReinaldo.n2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestAPI {

    private final String BASE_URL = "http://localhost:8080";

    // TESTE 1: Fazer Login com Sucesso
    @Test
    public void deveRetornarStatus200AoFazerLoginCorreto() {
        String payloadUsuario = "{\n" +
                "  \"nome\": \"Théo da Silva Sá\",\n" +
                "  \"cpf\": \"12345678910\",\n" +
                "  \"email\": \"login@email.com\",\n" +
                "  \"senha\": \"senha123\"\n" +
                "}";

        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(payloadUsuario)
                .post("/usuarios");

        String payloadLogin = "{\n" +
                "  \"email\": \"login@email.com\",\n" +
                "  \"senha\": \"senha123\"\n" +
                "}";

        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(payloadLogin)
                .when()
                .post("/usuarios/login")
                .then()
                .statusCode(200);
    }

    // TESTE 2: Salvar uma Venda
    @Test
    public void deveRetornarStatus200AoSalvarUmaVendaValida() {
        String payloadUsuario = "{\n" +
                "  \"nome\": \"Comprador Teste\",\n" +
                "  \"cpf\": \"10987654321\",\n" +
                "  \"email\": \"comprador@email.com\",\n" +
                "  \"senha\": \"123\"\n" +
                "}";

        Integer usuarioId = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(payloadUsuario)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(200)
                .extract().path("id");

        String payloadProduto = "{\n" +
                "  \"nome\": \"Placa Teste\",\n" +
                "  \"preco\": 500.00,\n" +
                "  \"categoria\": \"PLACA_MAE\"\n" +
                "}";

        Integer produtoId = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(payloadProduto)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200)
                .extract().path("id");

        String payloadVenda = "{\n" +
                "  \"valorTotal\": 500.00,\n" +
                "  \"formaPagamento\": \"PIX\",\n" +
                "  \"status\": \"PAGO\",\n" +
                "  \"usuario\": {\"id\": " + usuarioId + "},\n" +
                "  \"produto\": {\"id\": " + produtoId + "}\n" +
                "}";

        given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(payloadVenda)
                .when()
                .post("/vendas")
                .then()
                .statusCode(200);
    }
}
