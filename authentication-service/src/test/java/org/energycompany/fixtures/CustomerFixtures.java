package org.energycompany.fixtures;

import org.energycompany.model.auth.Customer;
import org.energycompany.model.auth.enums.CustomerStatus;
import org.energycompany.model.auth.enums.CustomerType;

import java.util.UUID;

public class CustomerFixtures {

    public static Customer getCustomer() {

        return Customer.builder()
                .id(UUID.randomUUID().toString())
                .email("valid.email@example.com")
                .firstName("John")
                .lastName("Doe")
                .customerStatus(CustomerStatus.ACTIVE)
                .customerType(CustomerType.CUSTOMER)
                .build();
    }
}
