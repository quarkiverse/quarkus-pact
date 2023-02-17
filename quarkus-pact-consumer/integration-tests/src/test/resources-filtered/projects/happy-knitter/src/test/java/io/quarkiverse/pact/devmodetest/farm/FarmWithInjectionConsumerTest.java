package io.quarkiverse.pact.devmodetest.farm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import io.quarkiverse.pact.testapp.AlpacaService;
import io.quarkiverse.pact.testapp.ConsumerAlpaca;
import io.quarkiverse.pact.testapp.Knitter;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "farm", port = "8085")
@PactDirectory("target/pacts")
@QuarkusTest // Needed to enable dependency injection of the rest client
public class FarmWithInjectionConsumerTest {

    // The consumer under test
    @Inject
    Knitter knitter;

    // A thin wrapper around the mock; normally we might not test this, here we want to test our mocking, so we do
    @RestClient
    @Inject
    AlpacaService alpacaService;

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
                .body(" {\n" +
                        "      \"colour\": \"black\",\n" +
                        "          \"name\": \"floppy\"\n" +
                        "        }")
                .toPact(V4Pact.class);
    }

    @Test
    public void testConsumption() {
        // This test is realistic(ish) of how a pact consumer might use the extension
        // It tests how the consumer under test uses what the mock passes back, *not* the mock itself
        String knitted = knitter.knit("irrelevant");
        assertEquals("a nice black sweater", knitted);
    }

    @Test
    public void testContractLooksCorrect() {
        // Be aware! Do not copy this test!
        // A normal pact consumer test should treat what comes back from pact, and use it to test the business logic of the consumer code
        // Normally there is no need to test the mock, because the consumer has just told the mock exactly what to do using Json or a DSL
        // Here, we're writing a pact engine so we *do* need to test the mock because that's the thing that might break :)

        ConsumerAlpaca alpaca = alpacaService.getByName("ignored-by-mock"); // Testing our RestClient service works as expected
        assertNotNull(alpaca);
        assertEquals(alpaca.getColour(), "black"); // Test of marginal value in 'real' cases; testing the json deserialization on the consumer
        assertEquals(alpaca.getName(), "floppy"); // Test of marginal value in 'real' cases; testing the json deserialization on the consumer
    }

    @Test
    @Disabled // With Quarkus 3, test methods cannot directly access Pact classes, because they are in different classloaders. See https://github.com/quarkiverse/quarkus-pact/issues/73
    // The good news is there are very few use cases where test code should be doing parameter injection of the mock server.
    public void testPortIsCorrect(MockServer mockServer) {
        // If we have a test, pact assumes we will call it and validates there was a call
        ConsumerAlpaca alpaca = alpacaService.getByName("fluffy");
        assertEquals(8085, mockServer.getPort()); // Testing the mock - don't do this normally!
    }
}
