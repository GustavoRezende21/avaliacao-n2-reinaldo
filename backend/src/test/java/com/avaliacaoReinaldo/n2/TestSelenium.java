package com.avaliacaoReinaldo.n2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestSelenium {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "http://localhost:5173";

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // TESTE 1: CADASTRO E LOGIN
    @Test
    public void teste01_deveCadastrarUsuarioEFazerLogin() throws InterruptedException {
        driver.get(BASE_URL + "/usuarios");
        Thread.sleep(1500);

        driver.findElement(By.xpath("//button[text()='Novo Usuário']")).click();
        Thread.sleep(1500);

        driver.findElement(By.xpath("//input[@placeholder='Nome Completo']")).sendKeys("Teste Login");
        driver.findElement(By.xpath("//input[@placeholder='CPF']")).sendKeys("11122233344");

        String email = "login@teste.com";
        driver.findElement(By.xpath("//input[@placeholder='E-mail']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@placeholder='Senha']")).sendKeys("123");
        Thread.sleep(1500);

        driver.findElement(By.xpath("//button[text()='Salvar']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        Thread.sleep(3000);

        driver.get(BASE_URL + "/");
        Thread.sleep(1500);

        driver.findElement(By.xpath("//input[@placeholder='seuemail@dominio.com']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@placeholder='******']")).sendKeys("123");
        Thread.sleep(1500);

        driver.findElement(By.xpath("//button[text()='Entrar']")).click();
        wait.until(ExpectedConditions.urlContains("/produtos"));
        Thread.sleep(2000);
    }

    // TESTE 2: FLUXO COMPLETO (CLIENTE -> PRODUTO -> VENDA)
    @Test
    public void teste02_deveRealizarVendaCompleta() throws InterruptedException {
        driver.get(BASE_URL + "/usuarios");
        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[text()='Novo Usuário']")).click();

        driver.findElement(By.xpath("//input[@placeholder='Nome Completo']")).sendKeys("Cliente VIP");
        driver.findElement(By.xpath("//input[@placeholder='CPF']")).sendKeys("99988877766");
        driver.findElement(By.xpath("//input[@placeholder='E-mail']")).sendKeys("vip@email.com");
        driver.findElement(By.xpath("//input[@placeholder='Senha']")).sendKeys("123");

        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[text()='Salvar']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        Thread.sleep(3000);

        driver.get(BASE_URL + "/produtos");
        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[text()='Novo Produto']")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//input[@placeholder='Nome']")).sendKeys("Ryzen 9 5900X");
        driver.findElement(By.xpath("//input[@placeholder='Preço']")).sendKeys("2500");

        Select dropdownCat = new Select(driver.findElement(By.tagName("select")));
        if (dropdownCat.getOptions().size() > 1) dropdownCat.selectByIndex(1);

        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[text()='Salvar']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-overlay")));

        Thread.sleep(3000);

        driver.get(BASE_URL + "/vendas");
        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[text()='Registrar Venda']")).click();
        Thread.sleep(1500);

        Select selCliente = new Select(driver.findElement(By.xpath("//label[text()='Cliente:']/following-sibling::select[1]")));
        selCliente.selectByVisibleText("Cliente VIP");
        Thread.sleep(1000);

        Select selProduto = new Select(driver.findElement(By.xpath("//label[text()='Produto:']/following-sibling::select[1]")));
        if(selProduto.getOptions().size() > 1) selProduto.selectByIndex(1);
        Thread.sleep(1000);

        Select selPagamento = new Select(driver.findElement(By.xpath("//label[text()='Forma de Pagamento:']/following-sibling::select[1]")));
        if(selPagamento.getOptions().size() > 1) selPagamento.selectByIndex(1);
        Thread.sleep(1000);

        Select selStatus = new Select(driver.findElement(By.xpath("//label[text()='Status:']/following-sibling::select[1]")));
        if(selStatus.getOptions().size() > 1) selStatus.selectByIndex(1);
        Thread.sleep(1500);

        driver.findElement(By.xpath("//button[text()='Finalizar']")).click();

        Thread.sleep(4000);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}