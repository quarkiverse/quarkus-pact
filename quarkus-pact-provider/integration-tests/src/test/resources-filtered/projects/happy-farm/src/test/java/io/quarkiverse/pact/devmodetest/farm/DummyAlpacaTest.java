package io.quarkiverse.pact.devmodetest.farm;

import io.quarkiverse.pact.testapp.IntegrationDevModeAlpaca;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
