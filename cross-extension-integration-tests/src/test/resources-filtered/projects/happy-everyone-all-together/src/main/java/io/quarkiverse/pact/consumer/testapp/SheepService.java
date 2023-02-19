package io.quarkiverse.pact.consumer.testapp;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("sheep")
@RegisterRestClient(configKey = "sheep-api")
@Consumes("application/json")
public interface SheepService {

    @GET
    ConsumerAlpaca getByName(String name);
}
