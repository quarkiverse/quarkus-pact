package io.quarkiverse.pact.testapp;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/alpaca")
@RegisterRestClient(configKey = "alpaca-api")
@Consumes("application/json")
public interface AlpacaService {

    @GET
    ConsumerAlpaca getByName(String name);
}
