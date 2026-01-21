package tests;

import base.BaseTest;
import models.PostModel;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes automatizados para o endpoint /posts
 * focados em validação de contrato e comportamento básico.
 */
public class PostsApiTest extends BaseTest {

    @Test
    void deveBuscarUmPostComSucesso() {

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("title", notNullValue())
            .body("body", notNullValue());
    }

    @Test
    void deveValidarContratoDoPost() {

        given()
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }

    @Test
void deveBuscarListaDePostsComSucesso() {

    given()
    .when()
        .get("/posts")
    .then()
        .statusCode(200)
        .body("$", hasSize(100))
        .body("[0].userId", notNullValue())
        .body("[0].id", notNullValue())
        .body("[0].title", notNullValue())
        .body("[0].body", notNullValue());
}

  @Test
    void deveValidarPostUsandoPojo() {
        PostModel post = given()
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .extract()
                .as(PostModel.class); 

        // Validações usando JUnit puro (Assertions)
        assertEquals(1, post.getId());
        assertEquals(1, post.getUserId());
        // Validando que o título não está vazio
        assert (post.getTitle() != null && !post.getTitle().isEmpty());
    }
}