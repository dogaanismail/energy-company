package org.energycompany.service;

import org.energycompany.common.AbstractBaseServiceTest;
import org.energycompany.fixtures.LoginRequestFixtures;
import org.energycompany.fixtures.TokenResponseFixtures;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.energycompany.model.CustomResponse;
import org.energycompany.model.auth.dto.request.LoginRequest;
import org.energycompany.model.auth.dto.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerLoginServiceTest extends AbstractBaseServiceTest {

    @InjectMocks
    private CustomerLoginService customerLoginService;

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Test
    void shouldLogin_WhenValidLoginRequest_ReturnsTokenResponse() {

        LoginRequest loginRequest = LoginRequestFixtures.getLoginRequest();
        TokenResponse tokenResponse = TokenResponseFixtures.getTokenResponse();

        CustomResponse<TokenResponse> customResponse = CustomResponse.successOf(tokenResponse);
        when(customerServiceClient.loginCustomer(any(LoginRequest.class))).thenReturn(customResponse);

        CustomResponse<TokenResponse> response = customerLoginService.login(loginRequest);

        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals(tokenResponse, response.getResponse());

        verify(customerServiceClient, times(1)).loginCustomer(any(LoginRequest.class));

    }

    @Test
    void shouldNotLogin_WhenInvalidLoginRequest_ReturnsErrorResponse() {

        LoginRequest loginRequest = new LoginRequest();
        CustomResponse<TokenResponse> errorResponse = CustomResponse.<TokenResponse>builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .isSuccess(false)
                .build();

        when(customerServiceClient.loginCustomer(any(LoginRequest.class))).thenReturn(errorResponse);

        CustomResponse<TokenResponse> response = customerLoginService.login(loginRequest);

        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getHttpStatus());
        assertNull(response.getResponse());

        verify(customerServiceClient, times(1)).loginCustomer(any(LoginRequest.class));
    }

}