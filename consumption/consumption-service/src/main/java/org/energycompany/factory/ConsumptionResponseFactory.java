package org.energycompany.factory;

import lombok.experimental.UtilityClass;
import org.energycompany.entity.Consumption;
import org.energycompany.entity.MeteringPoint;
import org.energycompany.model.consumption.ConsumptionResponse;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class ConsumptionResponseFactory {

    public static List<ConsumptionResponse> getConsumptionResponse(
            List<Consumption> consumptions,
            MeteringPoint meteringPoint
    ) {

        return consumptions.
                stream()
                .map(consumption -> ConsumptionResponse.builder()
                        .consumptionId(consumption.getId().toString())
                        .meteringPointId(consumption.getMeteringPointId().toString())
                        .customerId(consumption.getCustomerId().toString())
                        .address(meteringPoint.getAddress())
                        .amount(consumption.getAmount().toString())
                        .amountUnit(consumption.getAmountUnit().name())
                        .consumptionTime(consumption.getConsumptionTime().toString())
                        .build()).toList();
    }

    public ConsumptionResponse getConsumptionResponse(
            Consumption consumption,
            MeteringPoint meteringPoint,
            BigDecimal monthlyCostCentsPerKwh,
            BigDecimal monthlyCostCentsPerKwhWithVat,
            BigDecimal monthlyCostEurPerMwh,
            BigDecimal monthlyCostEurPerMwhWithVat) {

        return ConsumptionResponse.builder()
                .consumptionId(consumption.getId().toString())
                .meteringPointId(consumption.getMeteringPointId().toString())
                .customerId(consumption.getCustomerId().toString())
                .address(meteringPoint.getAddress())
                .amount(consumption.getAmount().toString())
                .amountUnit(consumption.getAmountUnit().name())
                .consumptionTime(consumption.getConsumptionTime().toString())
                .monthlyCostCentsPerKwh(monthlyCostCentsPerKwh.toString())
                .monthlyCostCentsPerKwhWithVat(monthlyCostCentsPerKwhWithVat.toString())
                .monthlyCostEurPerMwh(monthlyCostEurPerMwh.toString())
                .monthlyCostEurPerMwhWithVat(monthlyCostEurPerMwhWithVat.toString())
                .build();
    }
}
