package io.quarkiverse.pact.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import io.quarkus.maven.it.RunAndCheckMojoTestBase;
import io.quarkus.maven.it.continuoustesting.ContinuousTestingMavenTestUtils;
import io.quarkus.test.devmode.util.DevModeClient;

/**
 * Because Pact uses Kotlin under the covers, we see different behaviour
 * in continuous testing and normal maven modes. This test exercises the continuous
 * testing with Pact.
 * <p>
 * NOTE to anyone diagnosing failures in this test, to run a single method use:
 * <p>
 * mvn install -Dit.test=DevMojoIT#methodName
 */
@DisabledIfSystemProperty(named = "quarkus.test.native", matches = "true")
public class DevModeContractTestIT extends RunAndCheckMojoTestBase {

    protected void runAndCheck(String... options) throws MavenInvocationException, FileNotFoundException {
        // To avoid clashes with other tests, we use a non-default port
        runAndCheck(true, "-Dquarkus.http.test-port=8083");
    }

    protected void runAndCheck(boolean performCompile, String... options)
            throws MavenInvocationException, FileNotFoundException {
        run(performCompile, options);

        String resp = devModeClient.getHttpResponse();

        assertThat(resp).containsIgnoringCase("ready").containsIgnoringCase("application")
                .containsIgnoringCase("1.0-SNAPSHOT");

        String json = devModeClient.getHttpResponse("/alpaca");
        //Minimal sense check, the tests do the heavy validation
        assertThat(json).containsIgnoringCase("colour");
    }

    @Test
    public void testThatTheTestsPassed() throws MavenInvocationException, FileNotFoundException {
        //we also check continuous testing
        testDir = initProject("projects/happy-farm", "projects/happy-farm-processed");
        runAndCheck();

        await()
                .pollDelay(100, TimeUnit.MILLISECONDS)
                .atMost(5, TimeUnit.SECONDS)
                .until(() -> new DevModeClient().getHttpResponse("/alpaca").startsWith("{"));

        ContinuousTestingMavenTestUtils testingTestUtils = new ContinuousTestingMavenTestUtils();
        ContinuousTestingMavenTestUtils.TestStatus results = testingTestUtils.waitForNextCompletion();
        Assertions.assertEquals(2, results.getTestsPassed());
        Assertions.assertEquals(0, results.getTestsFailed());

    }

}
