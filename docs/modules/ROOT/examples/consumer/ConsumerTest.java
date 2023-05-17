import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import io.quarkus.test.junit.QuarkusTest;

// tag::include[]
// <1>
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "farm", port = "8085")
@PactDirectory("target/pacts")
@QuarkusTest // <2>
public class ConsumerTest {

    // <3>
    @Inject
    Knitter knitter;

    @Pact(provider = "farm", consumer = "knitter")
    public V4Pact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder.given("test GET") //<4>
                .uponReceiving("GET REQUEST")
                .path("/alpaca")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(" {\n" + // <5>
                        "      \"colour\": \"black\",\n" +
                        "          \"name\": \"floppy\"\n" +
                        "        }")
                .toPact(V4Pact.class);
    }

    @Test
    public void testConsumption() { //<6>
        String knitted = knitter.knit("irrelevant"); // <7>
        assertEquals("a nice striped sweater", knitted);
    }
}
// end::include[]
