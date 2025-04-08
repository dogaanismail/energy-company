package org.energycompany.model.metering_point;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeteringPointResponse {

    @JsonProperty("meteringPointId")
    private String meteringPointId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("customerId")
    private String customerId;
}
