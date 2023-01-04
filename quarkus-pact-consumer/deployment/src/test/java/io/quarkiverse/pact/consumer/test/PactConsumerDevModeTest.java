package io.quarkiverse.pact.consumer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import io.quarkus.test.QuarkusDevModeTest;

/**
 * Beware! This test is not a sufficiently rigorous test of dev
 * mode. It is very possible for this test to pass, and 'real'
 * dev mode to fail, because of classloading complexities.
 * In a dev mode test we would run against a test instance,
 * and here we are running against a dev instance, which may
 * also be why things are a bit different.
 * <p>
 * For a full test, run the integration tests.
 */
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "farm", hostInterface = "localhost", port = "8087")
@PactDirectory("target/pacts")
public class PactConsumerDevModeTest {

    // Start hot reload (DevMode) test with the extension loaded
    @RegisterExtension
    static final QuarkusDevModeTest devModeTest = new QuarkusDevModeTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    private static final String alpacaBody = " {\n" +
            "      \"colour\": \"black\",\n" +
            "          \"name\": \"floppy\"\n" +
            "        }";

    @Pact(provider = "farm", consumer = "knitter")
    public V4Pact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Here we define our mock, which is also our expectations for the provider

        return builder.given("test GET")
                .uponReceiving("GET REQUEST")
                .path("/alpaca")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(alpacaBody)
                .toPact(V4Pact.class);
    }

    @Test
    public void testContractLooksCorrect(MockServer mockServer) throws URISyntaxException, IOException, InterruptedException {
        // Be aware! Do not copy this test!
        // A normal pact consumer test should treat what comes back from pact, and use it to test the business logic of the consumer code
        // Normally there is no need to test the mock, because the consumer has just told the mock exactly what to do using Json or a DSL
        // Here, we're writing a pact engine so we *do* need to test the mock because that's the thing that might break :)

        HttpResponse<String> response = invokeService(mockServer);
        assertEquals(alpacaBody, response.body()); // Don't do this unless you're testing pact itself! This is just testing the mock!
    }

    @Test
    public void testContractLooksCorrectWithHardcodedPort() throws URISyntaxException, IOException, InterruptedException {
        // Be aware! Do not copy this test!
        // A normal pact consumer test should treat what comes back from pact, and use it to test the business logic of the consumer code
        // Normally there is no need to test the mock, because the consumer has just told the mock exactly what to do using Json or a DSL
        // Here, we're writing a pact engine so we *do* need to test the mock because that's the thing that might break :)

        HttpResponse<String> response = invokeService("http://localhost:8087/alpaca");
        assertEquals(alpacaBody, response.body()); // Don't do this unless you're testing pact itself! This is just testing the mock!
    }

    @Test
    public void testPortIsCorrect(MockServer mockServer) throws URISyntaxException, IOException, InterruptedException {
        // Dummy call so Pact doesn't complain we didn't use the mock
        invokeService(mockServer);
        // If we have a test, pact assumes we will call it and validates there was a call
        assertEquals(8087, mockServer.getPort()); // Testing the mock - don't do this normally!
    }

    private HttpResponse<String> invokeService(MockServer mockServer)
            throws URISyntaxException, IOException, InterruptedException {
        // We're testing what happens without @QuarkusTest, so do the http call the low level way without framework help
        HttpRequest request = HttpRequest.newBuilder(new URI(mockServer.getUrl() + "/alpaca")).GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response);
        return response;
    }

    private HttpResponse<String> invokeService(String url)
            throws URISyntaxException, IOException, InterruptedException {
        // We're testing what happens without @QuarkusTest, so do the http call the low level way without framework help
        // (Wondering where the @QuarkusTest version is? It's an integration test.)
        HttpRequest request = HttpRequest.newBuilder(new URI(url)).GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response);
        return response;
    }
}
