package org.energycompany.factory;

import lombok.experimental.UtilityClass;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class EleringElectricPriceResponseFactory {

    public static Map<YearMonth, EleringElectricPriceResponse> convertMonthlyElectricPricesToYearMonth(
            List<EleringElectricPriceResponse> monthlyElectricPrices) {

        return monthlyElectricPrices.stream()
                .collect(Collectors.toMap(
                        electricPriceResponse -> YearMonth.from(electricPriceResponse
                                .getFromDateTime()
                                .atZone(ZoneId.of("Europe/Tallinn"))),
                        electricPriceResponse -> electricPriceResponse
                ));
    }
}
