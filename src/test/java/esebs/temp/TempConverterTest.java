package esebs.temp;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.ws.rs.core.MediaType;

@QuarkusTest
class TempConverterTest {

    @Test
    void testFahrenheitConverter() {
        given()
                .when().get("/tempconverter/fahrenheit/0/C")
                .then()
                .statusCode(200)
                .body(is("32"));

        given()
                .when().get("/tempconverter/fahrenheit?temperature=0&unit=C")
                .then()
                .statusCode(200)
                .body(is("32"));
    }

    @Test
    void testCelsiusConverter() {
        given()
                .when().get("/tempconverter/celsius/32/F")
                .then()
                .statusCode(200)
                .body(is("0"));
        given()
                .when().get("/tempconverter/celsius?temperature=-40&unit=F")
                .then()
                .statusCode(200)
                .body(is("-40"));
    }

    @Test
    void testUnkonwn() {
        given()
                .when().get("/tempconverter/celsius/32/K")
                .then()
                .statusCode(404)
                .body(is("Unknown Unit: K"));
        given()
                .when().get("/tempconverter/celsius?temperature=-40&unit=K")
                .then()
                .statusCode(404)
                .body(is("Unknown Unit: K"));
    }

    @Test
    void testJson() {
        given()
                .contentType("application/json")
                .accept("application/json")
                .body("{\n\"temperature\": 0,\n\"unit\": \"C\"}")
                .when()
                .get("/tempconverter/celsius/j")
                .then()
                .statusCode(200)
                .body(is("100"));
    }
}
