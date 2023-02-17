package io.quarkiverse.pact.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import io.quarkus.maven.it.RunAndCheckMojoTestBase;
import io.quarkus.maven.it.continuoustesting.ContinuousTestingMavenTestUtils;
import io.quarkus.test.devmode.util.DevModeTestUtils;

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
@Disabled
public class DevModeContractTestIT extends RunAndCheckMojoTestBase {

    protected void runAndCheck(boolean performCompile, String... options)
            throws MavenInvocationException, FileNotFoundException {
        run(performCompile, options);

        String resp = DevModeTestUtils.getHttpResponse();

        assertThat(resp).containsIgnoringCase("ready").containsIgnoringCase("application")
                .containsIgnoringCase("1.0-SNAPSHOT");

        // There's no json endpoints, so nothing else to check
    }

    @Test
    public void testThatTheTestsPassed() throws MavenInvocationException, IOException {
        //we also check continuous testing
        String executionDir = "projects/happy-knitter-processed";
        testDir = initProject("projects/happy-knitter", executionDir);
        runAndCheck();

        ContinuousTestingMavenTestUtils testingTestUtils = new ContinuousTestingMavenTestUtils();
        ContinuousTestingMavenTestUtils.TestStatus results = testingTestUtils.waitForNextCompletion();
        // This is a bit brittle when we add tests, but failures are often so catastrophic they're not even reported as failures,
        // so we need to check the pass count explicitly
        Assertions.assertEquals(4, results.getTestsPassed());
        Assertions.assertEquals(0, results.getTestsFailed());

        // Now confirm a pact file got written by the pact consumer
        File targetFolder = new File("target/test-classes/" + executionDir + "/target");
        assertTrue(targetFolder.exists(), targetFolder.getAbsolutePath() + " should exist (is this a test code error?)");
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
