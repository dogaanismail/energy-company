package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.entity.Consumption;
import org.energycompany.entity.MeteringPoint;
import org.energycompany.enums.ResolutionEnum;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.energycompany.model.consumption.ConsumptionResponse;
import org.energycompany.repository.ConsumptionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumptionService {

    private final EleringAdapterService eleringAdapterService;
    private final ConsumptionRepository consumptionRepository;
    private final MeteringPointService meteringPointService;

    public List<ConsumptionResponse> getConsumptions(
            UUID customerId,
            UUID meteringPointId,
            int year) {

        Instant yearStart = LocalDate.of(year, 1, 1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();
        Instant yearEnd = LocalDate.of(year, 12, 31)
                .atTime(LocalTime.MAX)
                .atZone(ZoneOffset.UTC)
                .toInstant();

        List<EleringElectricPriceResponse> monthlyElectricPrices = eleringAdapterService
                .getElectricPrices(yearStart, yearEnd, ResolutionEnum.MONTH);
        log.info("Monthly electric prices, size: {}", monthlyElectricPrices.size());

        MeteringPoint meteringPoint = meteringPointService.getMeteringPoint(meteringPointId);
        List<Consumption> consumptions = consumptionRepository.findByCustomerIdAndMeteringPointIdAndConsumptionTimeBetween(
                customerId,
                meteringPointId,
                yearStart,
                yearEnd
        );

        return mapToResponse(consumptions, meteringPoint);
    }

    private List<ConsumptionResponse> mapToResponse(
            List<Consumption> consumptions,
            MeteringPoint meteringPoint) {

        return consumptions.
                stream()
                .map(consumption -> ConsumptionResponse.builder()
                        .consumptionId(consumption.getId().toString())
                        .meteringPointId(consumption.getMeteringPointId().toString())
                        .customerId(consumption.getCustomerId().toString())
                        .address(meteringPoint.getAddress())
                        .amount(consumption.getAmount().toString())
                        .amountUnit(consumption.getAmount_unit().name())
                        .consumptionTime(consumption.getConsumptionTime().toString())
                        .build()).toList();
    }
}
