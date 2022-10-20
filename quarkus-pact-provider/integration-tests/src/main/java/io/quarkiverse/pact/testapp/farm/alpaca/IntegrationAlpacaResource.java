package io.quarkiverse.pact.testapp.farm.alpaca;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/alpaca")
public class IntegrationAlpacaResource {

    // This is a non-cloud-safe way of handling session affinity.
    // For simplicity, it will do!
    private static final Map<String, IntegrationAlpaca> alpacas = new HashMap<>();

    private IntegrationAlpaca createAlpaca(String sessionId) {
        final IntegrationAlpaca fluffy = new IntegrationAlpaca();
        alpacas.put(sessionId, fluffy);
        fluffy.setColour("brown");
        fluffy.setName("flippy");
        return fluffy;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public IntegrationAlpaca getAlpaca(@CookieParam("alpaca-name") String alpacaName) {
        return Objects.requireNonNullElseGet(alpacas.get(alpacaName), () -> createAlpaca(alpacaName));
    }

}
