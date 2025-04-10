package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.enums.ResolutionEnum;
import org.energycompany.integration.elering.EleringAdapterClient;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static org.energycompany.config.CacheConfig.ELERING_ELECTRIC_PRICES_CACHE;

@Service
@RequiredArgsConstructor
public class EleringAdapterService {

    private final EleringAdapterClient eleringAdapterClient;

    @Cacheable(
            value = ELERING_ELECTRIC_PRICES_CACHE,
            key = "#startDateTime.toString() + #endDateTime.toString() + #resolution.name()"
    )
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
