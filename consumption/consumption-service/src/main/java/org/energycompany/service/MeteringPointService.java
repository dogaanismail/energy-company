package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.entity.MeteringPoint;
import org.energycompany.exception.MeteringPointNotFoundException;
import org.energycompany.model.metering_point.MeteringPointResponse;
import org.energycompany.repository.MeteringPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeteringPointService {

    private final MeteringPointRepository meteringPointRepository;

    public MeteringPoint getMeteringPoint(
            UUID meteringPointId) {

        Optional<MeteringPoint> optionalMeteringPoint = meteringPointRepository
                .findById(meteringPointId);

        if (optionalMeteringPoint.isEmpty()) {
            log.error("MeteringPoint with id {} not found", meteringPointId);
            throw new MeteringPointNotFoundException("Metering point not found for id:" + meteringPointId);
        }

        return optionalMeteringPoint.get();
    }

    public List<MeteringPointResponse> getMeteringPoints(
            UUID customerId) {

        return meteringPointRepository
                .getMeteringPointsByCustomerId(customerId)
                .stream()
                .map(this::mapToMeteringPointResponse)
                .toList();
    }

    private MeteringPointResponse mapToMeteringPointResponse(
            MeteringPoint meteringPoint) {

        return MeteringPointResponse.builder()
                .meteringPointId(meteringPoint.getId().toString())
                .address(meteringPoint.getAddress())
                .customerId(meteringPoint.getCustomerId().toString())
                .build();
    }
}
