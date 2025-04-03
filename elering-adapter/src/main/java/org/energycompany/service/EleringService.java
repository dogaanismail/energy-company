package org.energycompany.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.integration.elering.api.EleringApi;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.energycompany.integration.elering.enums.ResolutionEnum;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EleringService {

    private final EleringApi eleringApi;

    public List<EleringElectricPriceResponse> getElectricPrices(
            Instant startDateTime,
            Instant endDateTime,
            ResolutionEnum resolution) {

        return eleringApi.getElectricPrices(
                startDateTime,
                endDateTime,
                resolution.getResolutionValue());
    }
}
