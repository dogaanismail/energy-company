package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.energycompany.model.auth.Customer;
import org.energycompany.model.auth.dto.request.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final CustomerServiceClient customerServiceClient;

    public Customer registerCustomer(RegisterRequest registerRequest) {

        return customerServiceClient.register(registerRequest).getBody();
    }
}
