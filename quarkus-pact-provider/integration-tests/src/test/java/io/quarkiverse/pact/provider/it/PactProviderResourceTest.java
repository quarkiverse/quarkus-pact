package io.quarkiverse.pact.provider.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PactProviderResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/pact-provider")
                .then()
                .statusCode(200)
                .body(is("Hello pact-provider"));
    }
}
