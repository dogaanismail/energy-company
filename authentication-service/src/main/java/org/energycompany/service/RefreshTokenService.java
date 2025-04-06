package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.energycompany.model.auth.dto.request.TokenRefreshRequest;
import org.energycompany.model.auth.dto.response.TokenResponse;
import org.energycompany.model.CustomResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final CustomerServiceClient customerServiceClient;

    public CustomResponse<TokenResponse> refreshToken(TokenRefreshRequest tokenRefreshRequest) {

        return customerServiceClient.refreshToken(tokenRefreshRequest);
    }
}
