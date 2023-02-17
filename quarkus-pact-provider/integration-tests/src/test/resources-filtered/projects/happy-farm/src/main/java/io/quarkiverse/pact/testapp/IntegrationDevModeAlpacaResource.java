package io.quarkiverse.pact.testapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/alpaca")
public class IntegrationDevModeAlpacaResource {

    // This is a non-cloud-safe way of handling session affinity.
    // For simplicity, it will do!
    private static final Map<String, IntegrationDevModeAlpaca> alpacas = new HashMap<>();

    private IntegrationDevModeAlpaca createAlpaca(String sessionId) {
        final IntegrationDevModeAlpaca fluffy = new IntegrationDevModeAlpaca();
        alpacas.put(sessionId, fluffy);
        fluffy.setColour("black");
        fluffy.setName("fluffy");
        return fluffy;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public IntegrationDevModeAlpaca getAlpaca(@CookieParam("alpaca-name") String alpacaName) {
        return Objects.requireNonNullElseGet(alpacas.get(alpacaName), () -> createAlpaca(alpacaName));
    }

}
