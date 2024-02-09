package io.quarkiverse.pact.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import io.quarkus.maven.it.RunAndCheckMojoTestBase;
import io.quarkus.maven.it.continuoustesting.ContinuousTestingMavenTestUtils;
import io.quarkus.maven.it.verifier.RunningInvoker;
import io.quarkus.runtime.LaunchMode;

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

    private static final int DEFAULT_PORT = 8080;

    // See https://github.com/quarkiverse/quarkus-pact/issues/180
    // These tests OOM with Quarkus 3.7+, so we need to bump the amount of memory they have
    protected void run(boolean performCompile, LaunchMode mode, boolean skipAnalytics, String... options)
            throws FileNotFoundException, MavenInvocationException {
        assertThat(testDir).isDirectory();
        running = new RunningInvoker(testDir, false);

        final List<String> args = new ArrayList<>(3 + options.length);
        if (performCompile) {
            args.add("compile");
        }
        args.add("quarkus:" + mode.getDefaultProfile());
        if (skipAnalytics) {
            args.add("-Dquarkus.analytics.disabled=true");
        }

        // If the test has set a different port, pass that on to the application
        if (getPort() != DEFAULT_PORT) {
            int port = getPort();
            int testPort = getPort() + 1;
            args.add("-Dquarkus.http.port=" + port);
            args.add("-Dquarkus.http.test-port=" + testPort);
        }

        boolean hasDebugOptions = false;
        for (String option : options) {
            args.add(option);
            if (option.trim().startsWith("-Ddebug=") || option.trim().startsWith("-Dsuspend=")) {
                hasDebugOptions = true;
            }
        }
        if (!hasDebugOptions) {
            // if no explicit debug options have been specified, let's just disable debugging
            args.add("-Ddebug=false");
        }

        //we need to limit the memory consumption, as we can have a lot of these processes
        //running at once, if they add default to 75% of total mem we can easily run out
        //of physical memory as they will consume way more than what they need instead of
        //just running GC
        args.add("-Djvm.args='-Xmx140m'");
        running.execute(args, Map.of());
    }

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
        testDir = initProject("projects/happy-everyone-all-together", "projects/happy-everyone-all-together-processed");
        runAndCheck();

        await()
                .pollDelay(100, TimeUnit.MILLISECONDS)
                .atMost(5, TimeUnit.SECONDS)
                .until(() -> devModeClient.getHttpResponse("/alpaca").startsWith("{"));

        ContinuousTestingMavenTestUtils testingTestUtils = new ContinuousTestingMavenTestUtils();
        ContinuousTestingMavenTestUtils.TestStatus results = testingTestUtils.waitForNextCompletion();
        Assertions.assertEquals(6, results.getTestsPassed());
        Assertions.assertEquals(0, results.getTestsFailed());

    }

}
