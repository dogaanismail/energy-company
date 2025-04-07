package org.energycompany.controller;

import org.energycompany.common.BaseIntegrationTest;
import org.energycompany.enums.ResolutionEnum;
import org.energycompany.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wiremock.org.eclipse.jetty.http.HttpMethod;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.energycompany.utils.EleringRequestParamsUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetHourlyElectricPricesControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHourlyElectricPrices() throws Exception {

        String payload = JsonUtils.getJsonString("json/elering/electric-price/hourly_electric_prices_response.json");

        wireMockRule.stubFor(request(HttpMethod.GET.name(),
                urlPathMatching("/api/public/v1/energy-price/electricity"))
                .withQueryParam(START_DATE_PARAM, matching("2025-03-31T21:00:00(\\.\\d{3})?Z"))
                .withQueryParam(END_DATE_PARAM, equalTo("2025-04-04T20:59:59.999Z"))
                .withQueryParam(RESOLUTION_PARAM, equalTo(ResolutionEnum.HOUR.getResolutionValue()))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(payload)));

        mockMvc.perform(get("/api/v1/electric-price/electric-prices")
                        .param(START_DATE_PARAM, "2025-03-31T21:00:00.000Z")
                        .param(END_DATE_PARAM, "2025-04-04T20:59:59.999Z")
                        .param(RESOLUTION_PARAM, ResolutionEnum.HOUR.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", hasSize(24)))
                .andExpect(jsonPath("$.response[0].centsPerKwh").value("7.319"))
                .andExpect(jsonPath("$.response[0].centsPerKwhWithVat").value("8.92918"))
                .andExpect(jsonPath("$.response[0].eurPerMwh").value("73.19"))
                .andExpect(jsonPath("$.response[0].eurPerMwhWithVat").value("89.2918"))
                .andExpect(jsonPath("$.response[0].fromDateTime").value("2025-04-02T21:00:00Z"))
                .andExpect(jsonPath("$.response[0].toDateTime").value("2025-04-02T21:59:59.999999999Z"));
    }

}
