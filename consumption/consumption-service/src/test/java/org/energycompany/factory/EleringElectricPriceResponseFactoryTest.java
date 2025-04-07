package org.energycompany.factory;

import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.energycompany.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wiremock.com.fasterxml.jackson.core.type.TypeReference;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

class EleringElectricPriceResponseFactoryTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldReturnMonthlyElectricPricesToYearMonth() throws Exception {

        String payload = JsonUtils.getJsonString("json/elering/electric-price/monthly_electric_prices_response.json");
        List<EleringElectricPriceResponse> responses =
                OBJECT_MAPPER.readValue(payload, new TypeReference<>() {
                });

        Map<YearMonth, EleringElectricPriceResponse> monthlyElectricPrices =
                EleringElectricPriceResponseFactory.convertMonthlyElectricPricesToYearMonth(responses);

        Assertions.assertNotNull(monthlyElectricPrices);
        Assertions.assertEquals(12, monthlyElectricPrices.size());
    }
}