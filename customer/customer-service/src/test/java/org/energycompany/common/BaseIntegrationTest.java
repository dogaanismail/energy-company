package org.energycompany.common;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.energycompany.common.annotations.IntegrationTest;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseIntegrationTest extends IntegrationTest {

    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration
            .options()
            .port(8080)
            .notifier(new ConsoleNotifier(true)));

    @BeforeEach
    public void setup() {
        wireMockRule.start();
    }

    @AfterEach
    public void tearDown() {
        wireMockRule.resetAll();
        wireMockRule.stop();
    }
}
