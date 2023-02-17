package io.quarkiverse.pact.testapp;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Knitter {
    @RestClient
    @Inject
    AlpacaService alpacaService;

    public String knit(String name) {
        ConsumerAlpaca alpaca = alpacaService.getByName(name);
        String colour = alpaca.getColour();
        return "a nice " + colour + " sweater";
    }
}
