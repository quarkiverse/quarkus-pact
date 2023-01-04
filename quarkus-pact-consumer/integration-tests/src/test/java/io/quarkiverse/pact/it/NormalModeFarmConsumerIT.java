package io.quarkiverse.pact.it;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "farm", port = "8086")
@PactDirectory("target/pacts")
public class NormalModeFarmConsumerIT {

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
    public void testPortIsCorrectWithHardCodedPort(MockServer mockServer)
            throws URISyntaxException, IOException, InterruptedException {
        // Dummy call so Pact doesn't complain we didn't use the mock
        HttpResponse<String> response = invokeService(mockServer);
        // If we have a test, pact assumes we will call it and validates there was a call
        assertEquals(8086, mockServer.getPort()); // Testing the mock - don't do this normally!
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

}
