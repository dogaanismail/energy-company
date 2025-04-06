package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAuthenticationService {

    private final CustomerServiceClient customerServiceClient;

    public final UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(
            String token) {

        return customerServiceClient.getAuthentication(token);
    }
}
