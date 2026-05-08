package tests;

import config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de Autenticação — ReqRes API")
public class AuthTest extends BaseTest {

    @Test
    @DisplayName("POST /login — deve autenticar com credenciais válidas")
    void deveAutenticarComSucesso() {
        String body = """
                {
                    "email": "eve.holt@reqres.in",
                    "password": "cityslicka"
                }
                """;

        given()
                .spec(spec)
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("POST /login — deve retornar 400 sem senha")
    void deveRetornarErroSemSenha() {
        String body = """
                {
                    "email": "eve.holt@reqres.in"
                }
                """;

        given()
                .spec(spec)
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    @DisplayName("POST /register — deve registrar com sucesso")
    void deveRegistrarComSucesso() {
        String body = """
                {
                    "email": "eve.holt@reqres.in",
                    "password": "pistol"
                }
                """;

        given()
                .spec(spec)
                .body(body)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("POST /register — deve retornar 400 sem senha")
    void deveRetornarErroRegistroSemSenha() {
        String body = """
                {
                    "email": "sydney@fife"
                }
                """;

        given()
                .spec(spec)
                .body(body)
                .when()
                .post("/register")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}