package io.quarkiverse.pact.provider.test;

import org.eclipse.microprofile.config.inject.ConfigProperty;
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
import io.quarkus.test.QuarkusUnitTest;

@Provider("Farm")
@PactFolder("pacts")
public class PactProviderTest {

    @ConfigProperty(name = "quarkus.http.test-port")
    int quarkusPort;

    // Start unit test with the extension loaded
    // We also need to load in the pacts we will be using, since this extension changes the classloader of the test
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClasses(AlpacaResource.class, Alpaca.class)
                    .addAsResource(ClassLoader.getSystemClassLoader().getResource("pacts/knitter-farm.json"),
                            "pacts/knitter-farm.json"));

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
