package io.quarkiverse.pact.devmodetest.provider.farm;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.quarkiverse.pact.provider.testapp.IntegrationDevModeAlpaca;

/**
 * This test doesn't actually do anything useful, but if it's present when running dev mode tests,
 * we get more useful output out from failures in the templated contract test we care about.
 * Otherwise, classloading failures can be silent.
 */
public class DummyAlpacaTest {
    @Test
    public void visitShouldReturnSomething() {
        IntegrationDevModeAlpaca stubby = new IntegrationDevModeAlpaca();
        assertNotNull(stubby);
    }

}
