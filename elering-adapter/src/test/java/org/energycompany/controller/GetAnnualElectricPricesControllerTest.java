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

public class GetAnnualElectricPricesControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnWeeklyElectricPrices() throws Exception {

        String payload = JsonUtils.getJsonString("json/elering/electric-price/annual_electric_prices_response.json");

        wireMockRule.stubFor(request(HttpMethod.GET.name(),
                urlPathMatching("/api/public/v1/energy-price/electricity"))
                .withQueryParam(START_DATE_PARAM, matching("2012-12-31T22:00:00(\\.\\d{3})?Z"))
                .withQueryParam(END_DATE_PARAM, equalTo("2025-04-05T20:59:59.999Z"))
                .withQueryParam(RESOLUTION_PARAM, equalTo(ResolutionEnum.WEEK.getResolutionValue()))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(payload)));

        mockMvc.perform(get("/api/v1/electric-price/get-electric-prices")
                        .param(START_DATE_PARAM, "2012-12-31T22:00:00.000Z")
                        .param(END_DATE_PARAM, "2025-04-05T20:59:59.999Z")
                        .param(RESOLUTION_PARAM, ResolutionEnum.WEEK.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(13)))
                .andExpect(jsonPath("$[0].centsPerKwh").value("4.4502"))
                .andExpect(jsonPath("$[0].centsPerKwhWithVat").value("5.34031"))
                .andExpect(jsonPath("$[0].eurPerMwh").value("44.5026"))
                .andExpect(jsonPath("$[0].eurPerMwhWithVat").value("53.40319"))
                .andExpect(jsonPath("$[0].fromDateTime").value("2012-12-31T22:00:00Z"))
                .andExpect(jsonPath("$[0].toDateTime").value("2013-12-31T21:59:59.999999999Z"));
    }
}
