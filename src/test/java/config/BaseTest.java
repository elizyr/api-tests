package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification spec;

    @BeforeAll
    public static void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", "free_user_3DQ6frxyM8uxHq3JKs3NrnAzKjT")
                .build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}