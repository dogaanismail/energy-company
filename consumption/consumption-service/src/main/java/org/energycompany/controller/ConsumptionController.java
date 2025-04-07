package org.energycompany.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.model.CustomResponse;
import org.energycompany.model.consumption.ConsumptionResponse;
import org.energycompany.service.ConsumptionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(
        name = "Consumption Service ",
        description = "APIs that are exposed to fetch consumptions for a specific customer"
)
@RequestMapping("/api/v1/consumptions")
public class ConsumptionController {

    private final ConsumptionService consumptionService;

    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CustomResponse<List<ConsumptionResponse>> getConsumptions(
            @RequestParam(name = "customerId") UUID customerId,
            @RequestParam(name = "meteringPointId") UUID meteringPointId,
            @RequestParam(name = "year") int year
    ) {

        log.info("Received a request to fetch consumptions for a customer");
        List<ConsumptionResponse> consumptions = consumptionService
                .getConsumptions(customerId, meteringPointId, year);
        log.info("Consumptions fetched successfully, size: {}", consumptions.size());

        return CustomResponse.successOf(consumptions);
    }

}
