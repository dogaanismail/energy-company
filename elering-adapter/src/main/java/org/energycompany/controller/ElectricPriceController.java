package org.energycompany.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.energycompany.integration.elering.enums.ResolutionEnum;
import org.energycompany.service.EleringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

import static org.energycompany.utils.EleringRequestParamsUtils.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(
        name = "Elering Electric Price Controller",
        description = "APIs that are exposed to fetch electric prices from Elering"
)
@RequestMapping("/api/v1/electric-price")
public class ElectricPriceController {

    private final EleringService eleringService;

    @GetMapping("/get-electric-prices")
    public ResponseEntity<List<EleringElectricPriceResponse>> getElectricPrices(
            @RequestParam(name = START_DATE_PARAM) Instant startDateTime,
            @RequestParam(name = END_DATE_PARAM) Instant endDateTime,
            @RequestParam(name = RESOLUTION_PARAM) ResolutionEnum resolution
    ) {

        log.info("Received a request to fetch electric prices from Elering");
        List<EleringElectricPriceResponse> electricPrices = eleringService.getElectricPrices(
                startDateTime,
                endDateTime,
                resolution);
        log.info("Fetched {} electric prices from Elering", electricPrices.size());

        return ResponseEntity.ok(electricPrices);
    }
}
