package tests;

import config.BaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de Usuários — ReqRes API")
public class UserTest extends BaseTest {

    @Test
    @DisplayName("GET /users — deve retornar lista de usuários com status 200")
    void deveListarUsuarios() {
        given()
                .spec(spec)
                .when()
                .get("/users?page=1")
                .then()
                .statusCode(200)
                .body("data", not(empty()))
                .body("page", equalTo(1))
                .body("data[0].id", notNullValue())
                .body("data[0].email", notNullValue());
    }

    @Test
    @DisplayName("GET /users/{id} — deve retornar usuário existente")
    void deveRetornarUsuarioPorId() {
        given()
                .spec(spec)
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue());
    }

    @Test
    @DisplayName("GET /users/{id} — deve retornar 404 para usuário inexistente")
    void deveRetornar404ParaUsuarioInexistente() {
        given()
                .spec(spec)
                .when()
                .get("/users/9999")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("POST /users — deve criar novo usuário")
    void deveCriarUsuario() {
        String body = """
                {
                    "name": "Vanessa Meyer",
                    "job": "QA Analyst"
                }
                """;

        given()
                .spec(spec)
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Vanessa Meyer"))
                .body("job", equalTo("QA Analyst"))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("PUT /users/{id} — deve atualizar usuário")
    void deveAtualizarUsuario() {
        String body = """
                {
                    "name": "Vanessa Meyer",
                    "job": "QA Engineer"
                }
                """;

        given()
                .spec(spec)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("Vanessa Meyer"))
                .body("job", equalTo("QA Engineer"))
                .body("updatedAt", notNullValue());
    }

    @Test
    @DisplayName("DELETE /users/{id} — deve deletar usuário com status 204")
    void deveDeletarUsuario() {
        given()
                .spec(spec)
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }
}