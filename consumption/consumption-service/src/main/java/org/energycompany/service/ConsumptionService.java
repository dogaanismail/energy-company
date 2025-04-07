package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.entity.Consumption;
import org.energycompany.entity.MeteringPoint;
import org.energycompany.enums.ResolutionEnum;
import org.energycompany.factory.ConsumptionResponseFactory;
import org.energycompany.factory.YearFactory;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.energycompany.model.consumption.ConsumptionResponse;
import org.energycompany.repository.ConsumptionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumptionService {

    private final ConsumptionRepository consumptionRepository;
    private final EleringAdapterService eleringAdapterService;
    private final MeteringPointService meteringPointService;

    public List<ConsumptionResponse> getConsumptions(
            UUID customerId,
            UUID meteringPointId,
            int year) {

        Instant yearStartDateTime = YearFactory.getYearStartDateTime(year);
        Instant yearEndDateTime = YearFactory.getYearEndDateTime(year);

        MeteringPoint meteringPoint = meteringPointService.getMeteringPoint(meteringPointId);
        List<Consumption> consumptions = consumptionRepository.findByCustomerIdAndMeteringPointIdAndConsumptionTimeBetween(
                customerId,
                meteringPointId,
                yearStartDateTime,
                yearEndDateTime
        );

        if (!consumptions.isEmpty()) {

            List<EleringElectricPriceResponse> monthlyElectricPrices = eleringAdapterService
                    .getElectricPrices(yearStartDateTime, yearEndDateTime, ResolutionEnum.MONTH);
            log.info("Monthly electric prices, size: {}", monthlyElectricPrices.size());

            //TODO: What if monthlyElectricPrices is empty or size is 0?

            return ConsumptionCalculationService.calculateCostAndGetConsumptionResponse(
                    consumptions,
                    meteringPoint,
                    monthlyElectricPrices);
        }

        return ConsumptionResponseFactory.getConsumptionResponse(consumptions, meteringPoint);
    }

}
