package org.energycompany.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenClaims {

    JWT_ID("jti"),
    CUSTOMER_ID("customerId"),
    CUSTOMER_TYPE("customerType"),
    CUSTOMER_STATUS("customerStatus"),
    CUSTOMER_FIRST_NAME("customerFirstName"),
    CUSTOMER_LAST_NAME("customerLastName"),
    CUSTOMER_EMAIL("customerEmail"),
    STORE_TITLE("storeTitle"),
    ISSUED_AT("iat"),
    EXPIRES_AT("exp"),
    ALGORITHM("alg"),
    TYP("typ");

    private final String value;

}
