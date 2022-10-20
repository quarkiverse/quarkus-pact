package io.quarkiverse.pact.provider.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import io.quarkiverse.pact.testapp.farm.alpaca.Alpaca;
import io.quarkiverse.pact.testapp.farm.alpaca.AlpacaResource;
import io.quarkus.test.QuarkusDevModeTest;

@Provider("Farm")
@PactFolder("pacts")
/**
 * Beware! This test is not a sufficiently rigorous test of dev
 * mode. It is very possible for this test to pass, and 'real'
 * dev mode to fail, because of classloading complexities.
 * In a dev mode test we would run against a test instance,
 * and here we are running against a dev instance, which may
 * also be why things are a bit different.
 *
 * For a full test, run the integration tests.
 */
public class PactProviderDevModeTest {

    // Note that this is the port for the *dev* instance, not the *test* instance (which here isn't started)
    // Injecting it doesn't work, presumably because the property is initialised after this is set
    int quarkusPort = 8080;

    // Start hot reload (DevMode) test with the extension loaded
    @RegisterExtension
    static final QuarkusDevModeTest devModeTest = new QuarkusDevModeTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClasses(AlpacaResource.class, Alpaca.class));

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", quarkusPort));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}
