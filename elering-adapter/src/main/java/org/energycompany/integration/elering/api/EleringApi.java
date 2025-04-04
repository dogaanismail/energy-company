package org.energycompany.integration.elering.api;

import feign.Headers;
import org.energycompany.integration.elering.dto.EleringElectricPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.energycompany.utils.EleringRequestParamsUtils.*;
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
            @RequestParam(name = START_DATE_PARAM) String startDateTime,
            @RequestParam(name = END_DATE_PARAM) String endDateTime,
            @RequestParam(name = RESOLUTION_PARAM) String resolution
    );
}
