package com.testingapi;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.Matchers.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;

class ApplicationTest {

    @Test
    public void verificarEstadoHTTP() {
        // Establece la base URI para la API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Realiza una solicitud GET al endpoint específico
        RestAssured
                .given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200); // Verifica que el estado HTTP sea 200
    }

    @Test
    public void validarContenidoRespuesta() {
        // Establece la base URI para la API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Realiza una solicitud GET al endpoint específico
        RestAssured
                .given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200) // Verifica que el estado HTTP sea 200
                .assertThat()
                .body("userId", equalTo(1)); // Verifica que el campo "userId" sea igual a 1
    }

    @Test
    public void verificarCabeceraRespuesta() {
        // Establece la base URI para la API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Realiza una solicitud GET al endpoint específico
        RestAssured
                .given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200) // Verifica que el estado HTTP sea 200
                .header("Content-Type", "application/json; charset=utf-8"); // Verifica la cabecera Content-Type
    }

    @Test
    public void realizarSolicitudPOST() {
        // Establece la base URI para la API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Cuerpo de la solicitud en formato JSON
        String requestBody = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";

        // Realiza una solicitud POST al endpoint específico con el cuerpo de la
        // solicitud
        RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201) // Verifica que el estado HTTP sea 201 (creado)
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1));
    }

    @Test
    public void verificarEstadoOtroRecurso() {
        // Establece la base URI para la API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Realiza una solicitud GET al endpoint específico
        RestAssured
                .given()
                .when()
                .get("/comments/5")
                .then()
                .statusCode(200); // Verifica que el estado HTTP sea 200
    }

    @Test
    public void validarContenidoRespuestaLista() {
        // Establece la base URI para la API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Realiza una solicitud GET al endpoint específico
        RestAssured
                .given()
                .when()
                .get("/todos")
                .then()
                .statusCode(200) // Verifica que el estado HTTP sea 200
                .assertThat()
                .body("[0].userId", equalTo(1)); // Verifica que el userId del primer 'todo' sea igual a 1
    }

    @Test
    public void verifyMultiplePosts() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        
        RestAssured
                .given()
                .when()
                .get("/posts?userId=1")
                .then()
                .body("size()", greaterThan(5));
    }

    @Test
    public void ejercicio1(){
        // Crear una instancia del ChromeDriver con las opciones configuradas
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        // Navega a DuckDuckGo
        driver.get("https://duckduckgo.com/settings");

        // Localiza y haz clic en "Idioma de la Región"
        WebElement languageRegion = driver.findElement(By.id("setting_kad"));
        languageRegion.click();

        // Localiza y selecciona "Español de España"
        WebElement spanishSpainOption = driver.findElement(By.xpath("//option[text()='Español (España)']"));
        spanishSpainOption.click();

        // Verifica que el idioma haya sido cambiado exitosamente
        WebElement confirmationText = driver.findElement(By.id("setting_kad"));
        if (confirmationText.getText().contains("Español (España)")) {
            System.out.println("El idioma se cambió exitosamente a Español de España.");
        } else {
            System.out.println("El cambio de idioma no se realizó correctamente.");
        }

        // Cierra el navegador
        driver.quit();
    }

}
