package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.entity.CustomerEntity;
import org.energycompany.exception.CustomerNotFoundException;
import org.energycompany.exception.CustomerStatusNotValidException;
import org.energycompany.model.Token;
import org.energycompany.model.auth.dto.request.TokenRefreshRequest;
import org.energycompany.model.auth.enums.CustomerStatus;
import org.energycompany.enums.TokenClaims;
import org.energycompany.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final CustomerRepository customerRepository;
    private final TokenService tokenService;

    public Token refreshToken(TokenRefreshRequest tokenRefreshRequest) {

        tokenService.verifyAndValidate(tokenRefreshRequest.getRefreshToken());

        String customerId = tokenService
                .getPayload(tokenRefreshRequest.getRefreshToken())
                .get(TokenClaims.CUSTOMER_ID.getValue())
                .toString();

        CustomerEntity customerEntityFromDB = customerRepository
                .findById(UUID.fromString(customerId))
                .orElseThrow(CustomerNotFoundException::new);

        this.validateCustomerStatus(customerEntityFromDB);

        return tokenService.generateToken(
                customerEntityFromDB.getClaims(),
                tokenRefreshRequest.getRefreshToken()
        );

    }

    private void validateCustomerStatus(CustomerEntity customerEntity) {

        if (!(CustomerStatus.ACTIVE.equals(customerEntity.getCustomerStatus()))) {
            throw new CustomerStatusNotValidException("CustomerStatus = " + customerEntity.getCustomerStatus());
        }
    }

}
