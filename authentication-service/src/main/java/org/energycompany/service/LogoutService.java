package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.energycompany.model.auth.dto.request.TokenInvalidateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final CustomerServiceClient customerServiceClient;

    public void logout(TokenInvalidateRequest tokenInvalidateRequest) {

        customerServiceClient.logout(tokenInvalidateRequest);
    }
}
