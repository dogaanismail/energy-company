package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.enums.ResolutionEnum;
import org.energycompany.integration.elering.EleringAdapterClient;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EleringAdapterService {

    private final EleringAdapterClient eleringAdapterClient;

    public List<EleringElectricPriceResponse> getElectricPrices(
            Instant startDateTime,
            Instant endDateTime,
            ResolutionEnum resolution
    ) {

        return eleringAdapterClient
                .getElectricPrices(startDateTime, endDateTime, resolution)
                .getResponse();
    }
}
