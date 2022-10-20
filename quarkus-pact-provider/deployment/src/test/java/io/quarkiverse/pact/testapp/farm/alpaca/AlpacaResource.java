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
public class AlpacaResource {

    // This is a non-cloud-safe way of handling session affinity.
    // For simplicity, it will do!
    private static final Map<String, Alpaca> alpacas = new HashMap<>();

    private Alpaca createAlpaca(String sessionId) {
        final Alpaca fluffy = new Alpaca();
        fluffy.setColour("black");
        fluffy.setName("fluffy");
        alpacas.put(sessionId, fluffy);
        return fluffy;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Alpaca getAlpaca(@CookieParam("alpaca-name") String alpacaName) {
        return Objects.requireNonNullElseGet(alpacas.get(alpacaName), () -> createAlpaca(alpacaName));
    }

}
