package org.energycompany.integration.elering.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EleringElectricPriceResponse {

    @JsonProperty("centsPerKwh")
    private String centsPerKwh;

    @JsonProperty("centsPerKwhWithVat")
    private String centsPerKwhWithVat;

    @JsonProperty("eurPerMwh")
    private String eurPerMwh;

    @JsonProperty("eurPerMwhWithVat")
    private String eurPerMwhWithVat;

    @JsonProperty("fromDateTime")
    private Instant fromDateTime;

    @JsonProperty("toDateTime")
    private Instant toDateTime;
}
