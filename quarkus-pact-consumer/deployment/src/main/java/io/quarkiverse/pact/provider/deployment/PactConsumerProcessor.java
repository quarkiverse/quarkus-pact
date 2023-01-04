package io.quarkiverse.pact.consumer.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class PactConsumerProcessor {

    private static final String FEATURE = "pact-consumer";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }
}
