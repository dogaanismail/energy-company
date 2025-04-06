package org.energycompany.factory;

import lombok.experimental.UtilityClass;
import org.energycompany.entity.CustomerEntity;
import org.energycompany.model.auth.dto.request.RegisterRequest;
import org.energycompany.model.auth.enums.CustomerType;

@UtilityClass
public class CustomerEntityFactory {

    public static CustomerEntity getCustomerEntityByRegisterRequest(
            RegisterRequest registerRequest) {

        CustomerType userType = "admin".equalsIgnoreCase(registerRequest.getRole())
                ? CustomerType.ADMIN
                : CustomerType.CUSTOMER;

        return CustomerEntity.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .customerType(userType)
                .build();
    }
}
