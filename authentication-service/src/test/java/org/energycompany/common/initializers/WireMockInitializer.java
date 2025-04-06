package org.energycompany.common.initializers;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final WireMockServer WIRE_MOCK_SERVER =
            new WireMockServer(WireMockConfiguration.wireMockConfig().port(0));

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        WIRE_MOCK_SERVER.start();

        TestPropertyValues.of("wiremock.server.port=" + WIRE_MOCK_SERVER.port())
                .applyTo(configurableApplicationContext.getEnvironment());

        WireMock.configureFor(WIRE_MOCK_SERVER.port());

        WireMock.stubFor(WireMock.get("/actuator/health")
                .willReturn(ResponseDefinitionBuilder.okForJson("{\"status\":\"UP\"}")));
    }
}
