package org.energycompany.model.consumption;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsumptionResponse {

    @JsonProperty("consumptionId")
    private String consumptionId;

    @JsonProperty("meteringPointId")
    private String meteringPointId;

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("amountUnit")
    private String amountUnit;

    @JsonProperty("consumptionTime")
    private String consumptionTime;
}
