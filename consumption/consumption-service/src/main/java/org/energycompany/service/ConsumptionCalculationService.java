package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.entity.Consumption;
import org.energycompany.entity.MeteringPoint;
import org.energycompany.factory.ConsumptionResponseFactory;
import org.energycompany.factory.EleringElectricPriceResponseFactory;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.energycompany.model.consumption.ConsumptionResponse;
import org.energycompany.utils.YearMonthUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConsumptionCalculationService {

    public static List<ConsumptionResponse> calculateCostAndGetConsumptionResponse(
            List<Consumption> consumptions,
            MeteringPoint meteringPoint,
            List<EleringElectricPriceResponse> monthlyElectricPrices) {

        Map<YearMonth, EleringElectricPriceResponse> pricesByYearMonth = EleringElectricPriceResponseFactory
                .convertMonthlyElectricPricesToYearMonth(monthlyElectricPrices);

        return consumptions.stream()
                .map(consumption -> {

                    YearMonth consumptionYearMonth =
                            YearMonthUtils.getYearMonthFromDate(consumption.getConsumptionTime());

                    EleringElectricPriceResponse monthlyPrice =
                            pricesByYearMonth.get(consumptionYearMonth);

                    BigDecimal centsPerKwh = new BigDecimal(monthlyPrice.getCentsPerKwh());
                    BigDecimal centsPerKwhWithVat = new BigDecimal(monthlyPrice.getCentsPerKwhWithVat());
                    BigDecimal eurPerMwh = new BigDecimal(monthlyPrice.getEurPerMwh());
                    BigDecimal eurPerMwhWithVat = new BigDecimal(monthlyPrice.getEurPerMwhWithVat());

                    // Convert the consumption amount from kWh to MWh
                    BigDecimal consumptionInKWh = consumption.getAmount();
                    BigDecimal consumptionInMWh = consumptionInKWh
                            .divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP);

                    BigDecimal monthlyCostCentsPerKwh = consumptionInKWh.multiply(centsPerKwh);
                    BigDecimal monthlyCostCentsPerKwhWithVat = consumptionInKWh.multiply(centsPerKwhWithVat);

                    BigDecimal monthlyCostEurPerMwh = consumptionInMWh.multiply(eurPerMwh);
                    BigDecimal monthlyCostEurPerMwhWithVat = consumptionInMWh.multiply(eurPerMwhWithVat);

                    return ConsumptionResponseFactory.getConsumptionResponse(
                            consumption,
                            meteringPoint,
                            monthlyCostCentsPerKwh,
                            monthlyCostCentsPerKwhWithVat,
                            monthlyCostEurPerMwh,
                            monthlyCostEurPerMwhWithVat);

                })
                .toList();
    }
}
