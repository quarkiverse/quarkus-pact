package io.quarkiverse.pact.testapp;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/alpaca")
@RegisterRestClient(configKey = "alpaca-api")
@Consumes("application/json")
public interface AlpacaService {

    @GET
    ConsumerAlpaca getByName(String name);
}
