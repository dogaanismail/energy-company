package org.energycompany.constants;

import lombok.Getter;

@Getter
public enum EurekaServerServices {

    API_GATEWAY("api_gateway"),
    CONSUMPTION_SERVICE("consumption_service"),
    CUSTOMER_SERVICE("customer_service"),
    AUTHENTICATION_SERVICE("authentication_service");

    private final String serviceName;

    EurekaServerServices(String serviceName) {
        this.serviceName = serviceName;
    }
}
