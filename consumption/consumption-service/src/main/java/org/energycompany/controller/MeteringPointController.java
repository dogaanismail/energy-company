package org.energycompany.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.model.CustomResponse;
import org.energycompany.model.metering_point.MeteringPointResponse;
import org.energycompany.service.MeteringPointService;
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
        name = "Metering Point Controller",
        description = "APIs that are exposed to fetch metering points"
)
@RequestMapping("/api/v1/metering-points")
public class MeteringPointController {

    private final MeteringPointService meteringPointService;

    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CustomResponse<List<MeteringPointResponse>> getMeteringPoints(
            @RequestParam(name = "customerId") UUID customerId
    ) {

        log.info("Received a request to fetch metering points for a customer");
        List<MeteringPointResponse> meteringPoints = meteringPointService
                .getMeteringPoints(customerId);
        log.info("Metering points fetched successfully, size: {}", meteringPoints.size());

        return CustomResponse.successOf(meteringPoints);
    }
}
