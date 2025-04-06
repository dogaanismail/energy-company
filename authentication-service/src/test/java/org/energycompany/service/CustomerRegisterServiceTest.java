package org.energycompany.service;

import org.energycompany.common.AbstractBaseServiceTest;
import org.energycompany.fixtures.CustomerFixtures;
import org.energycompany.fixtures.RegisterRequestFixtures;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.energycompany.model.auth.Customer;
import org.energycompany.model.auth.dto.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerRegisterServiceTest extends AbstractBaseServiceTest {

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Test
    void shouldRegisterCustomer_WhenValidRegisterRequest_ReturnsCustomer() {

        RegisterRequest registerRequest = RegisterRequestFixtures.getRegisterRequest();

        Customer expectedCustomer = CustomerFixtures.getCustomer();

        when(customerServiceClient.register(any(RegisterRequest.class)))
                .thenReturn(ResponseEntity.ok(expectedCustomer));

        Customer result = registerService.registerCustomer(registerRequest);

        assertNotNull(result);
        assertEquals(expectedCustomer, result);

        verify(customerServiceClient, times(1)).register(any(RegisterRequest.class));
    }

    @Test
    void shouldNotRegisterCustomer_WhenInvalidRegisterRequest_ReturnsNull() {

        RegisterRequest registerRequest = RegisterRequestFixtures.getRegisterRequestWithInvalidEmail();

        when(customerServiceClient.register(any(RegisterRequest.class)))
                .thenReturn(ResponseEntity.badRequest().build());

        Customer result = registerService.registerCustomer(registerRequest);
        assertNull(result);

        verify(customerServiceClient, times(1)).register(any(RegisterRequest.class));
    }

}