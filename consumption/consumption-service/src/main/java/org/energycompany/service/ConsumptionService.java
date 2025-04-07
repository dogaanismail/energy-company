package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.entity.Consumption;
import org.energycompany.entity.MeteringPoint;
import org.energycompany.exception.MeteringPointNotFoundException;
import org.energycompany.model.consumption.ConsumptionResponse;
import org.energycompany.repository.ConsumptionRepository;
import org.energycompany.repository.MeteringPointRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private final EleringAdapterService eleringAdapterService;
    private final ConsumptionRepository consumptionRepository;
    private final MeteringPointRepository meteringPointRepository;

    public List<ConsumptionResponse> getConsumptions(
            UUID customerId,
            UUID meteringPointId,
            Instant startDateTime,
            Instant endDateTime) {

        Optional<MeteringPoint> optionalMeteringPoint = meteringPointRepository
                .findById(meteringPointId);

        if (optionalMeteringPoint.isEmpty()) {
            throw new MeteringPointNotFoundException("Metering point not found for id:" + meteringPointId);
        }

        MeteringPoint meteringPoint = optionalMeteringPoint.get();
        List<Consumption> consumptions = consumptionRepository.findByCustomerIdAndMeteringPointIdAndConsumptionTimeBetween(
                customerId,
                meteringPointId,
                startDateTime,
                endDateTime
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
