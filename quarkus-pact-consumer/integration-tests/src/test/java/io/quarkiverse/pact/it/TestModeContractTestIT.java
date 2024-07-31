package io.quarkiverse.pact.it;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
 * <p>
 * NOTE to anyone diagnosing failures in this test, to run a single method use:
 * <p>
 * mvn install -Dit.test=DevMojoIT#methodName
 */
@DisabledIfSystemProperty(named = "quarkus.test.native", matches = "true")
public class TestModeContractTestIT extends RunAndCheckMojoTestBase {

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

    @Test
    public void testThatTheTestsPassed() throws MavenInvocationException, IOException {
        //we also check continuous testing
        String executionDir = "projects/happy-knitter-processed-testmode";
        testDir = initProject("projects/happy-knitter", executionDir);
        runAndCheck();

        ContinuousTestingMavenTestUtils testingTestUtils = new TestModeContinuousTestingMavenTestUtils(running);
        ContinuousTestingMavenTestUtils.TestStatus results = testingTestUtils.waitForNextCompletion();
        // This is a bit brittle when we add tests, but failures are often so catastrophic they're not even reported as failures,
        // so we need to check the pass count explicitly
        Assertions.assertEquals(5, results.getTestsPassed());
        Assertions.assertEquals(0, results.getTestsFailed());

        // Now confirm a pact file got written by the pact consumer
        File targetFolder = new File("target/test-classes/" + executionDir + "/target");
        assertTrue(targetFolder.exists(), targetFolder.getAbsolutePath() + " should exist (is this a test code error?)");

        // This is hardcoded in the pact file, so we have to share a name with the other test, but the parent folder is different
        File pactFolder = new File(targetFolder, "devmode-pacts");
        assertTrue(pactFolder.exists(), pactFolder.getAbsolutePath() + " should exist");
        File pactFile = new File(pactFolder, "knitter-farm.json");
        assertTrue(pactFile.exists(), pactFile.getAbsolutePath() + " should exist");

        // We don't need to make detailed assertions about the behaviour of pact, but check the contract has some content,
        // showing that the pact code didn't die trying to write it
        long fileSize = Files.size(Paths.get(pactFile.getAbsolutePath()));
        assertTrue(fileSize > 100, "The pact file has size " + fileSize + " but it should be bigger than that.");

    }

}
