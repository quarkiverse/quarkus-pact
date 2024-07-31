package io.quarkiverse.pact.it;

import java.io.FileNotFoundException;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import io.quarkus.maven.it.RunAndCheckMojoTestBase;
import io.quarkus.maven.it.continuoustesting.ContinuousTestingMavenTestUtils;
import io.quarkus.maven.it.continuoustesting.TestModeContinuousTestingMavenTestUtils;
import io.quarkus.runtime.LaunchMode;

/**
 * Because Pact uses Kotlin under the covers, we see different behaviour
 * in continuous testing and normal maven modes. This test exercises the continuous
 * testing with Pact.
 * We also see different behaviour in test mode and dev mode,
 * so we need to explicitly try test mode.
 * <p>
 * NOTE to anyone diagnosing failures in this test, to run a single method use:
 * <p>
 * mvn install -Dit.test=DevMojoIT#methodName
 *
 * Why does this class have a strange name? Good question.
 * There's some cross-talk between the tests, so that if this test runs, and takes more than a few milliseconds,
 * the NormalModeContractIT test fails. It fails with a message that the resources project's classes
 * can't be found ... even though the NormalModeContractIT test doesn't even use them.
 *
 * Changing this class so that the name doesn't start with Test works around the issue.
 */
@DisabledIfSystemProperty(named = "quarkus.test.native", matches = "true")
public class TheTestModeContractTestIT extends RunAndCheckMojoTestBase {

    @Override
    protected LaunchMode getDefaultLaunchMode() {
        return LaunchMode.TEST;
    }

    @Override
    public void shutdownTheApp() {
        if (running != null) {
            running.stop();
        }

        // There's no http server, so there's nothing to check to make sure we're stopped, except by the maven invoker itself, or the logs
    }

    /**
     * This is actually more like runAndDoNotCheck, because
     * we can't really check anything via a HTTP get, because this is a test mode application
     */
    @Override
    protected void runAndCheck(boolean performCompile, LaunchMode mode, String... options)
            throws FileNotFoundException, MavenInvocationException {
        run(performCompile, mode, options);

        // We don't need to try and pause, because the continuous testing utils will wait for tests to finish

    }

    protected void runAndCheck(String... options) throws MavenInvocationException, FileNotFoundException {
        // To avoid clashes with other tests, we use a non-default port
        runAndCheck(true, "-Dquarkus.http.test-port=8088");
    }

    @Test
    public void testThatTheTestsPassed() throws MavenInvocationException, FileNotFoundException {

        testDir = initProject("projects/happy-everyone-all-together",
                "projects/happy-everyone-all-together-testmode-processed");
        runAndCheck();

        TestModeContinuousTestingMavenTestUtils testingTestUtils = new TestModeContinuousTestingMavenTestUtils(running);

        ContinuousTestingMavenTestUtils.TestStatus results = testingTestUtils.waitForNextCompletion();
        Assertions.assertEquals(7, results.getTestsPassed());
        Assertions.assertEquals(0, results.getTestsFailed());

    }

}
