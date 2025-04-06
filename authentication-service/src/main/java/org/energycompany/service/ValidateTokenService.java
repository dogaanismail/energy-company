package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateTokenService {

    private final CustomerServiceClient customerServiceClient;

    public void validateToken(String token) {
        customerServiceClient.validateToken(token);
    }
}
