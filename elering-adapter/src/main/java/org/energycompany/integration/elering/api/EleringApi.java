package org.energycompany.integration.elering.api;

import feign.Headers;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(
        name = "elering-api",
        url = "${integration.elering.url}"
)
@Headers({"Content-Type: application/json"})
public interface EleringApi {

    @GetMapping(
            value = "api/public/v1/energy-price/electricity",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    List<EleringElectricPriceResponse> getElectricPrices(
            @RequestParam(name = "startDateTime") Instant startDateTime,
            @RequestParam(name = "endDateTime") Instant endDateTime,
            @RequestParam(name = "resolution") String resolution
    );
}
