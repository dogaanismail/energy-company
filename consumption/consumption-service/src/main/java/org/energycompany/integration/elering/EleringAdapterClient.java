package org.energycompany.integration.elering;

import feign.Headers;
import org.energycompany.enums.ResolutionEnum;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.energycompany.model.CustomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@FeignClient(
        name = "elering-adapter",
        path = "/api/v1/electric-price"
)
@Headers({"Content-Type: application/json"})
public interface EleringAdapterClient {

    @GetMapping(value = "/electric-prices")
    CustomResponse<List<EleringElectricPriceResponse>> getElectricPrices(
            @RequestParam(name = "startDateTime") Instant startDateTime,
            @RequestParam(name = "endDateTime") Instant endDateTime,
            @RequestParam(name = "resolution") ResolutionEnum resolution
    );
}
