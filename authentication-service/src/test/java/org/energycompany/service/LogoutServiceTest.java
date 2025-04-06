package org.energycompany.service;

import org.energycompany.common.AbstractBaseServiceTest;
import org.energycompany.fixtures.TokenInvalidateRequestFixtures;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.energycompany.model.CustomResponse;
import org.energycompany.model.auth.dto.request.TokenInvalidateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LogoutServiceTest extends AbstractBaseServiceTest {

    @InjectMocks
    private LogoutService logoutService;

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Test
    void shouldLogout_WhenValidTokenInvalidateRequest_ReturnsSuccess() {

        TokenInvalidateRequest tokenInvalidateRequest = TokenInvalidateRequestFixtures.getTokenInvalidateRequest();
        CustomResponse<Void> expectedResponse = CustomResponse.<Void>builder()
                .httpStatus(HttpStatus.OK)
                .isSuccess(true)
                .build();

        when(customerServiceClient.logout(any(TokenInvalidateRequest.class)))
                .thenReturn(expectedResponse);

        CustomResponse<Void> result = logoutService.logout(tokenInvalidateRequest);

        assertNotNull(result);
        assertTrue(result.getIsSuccess());
        assertEquals(HttpStatus.OK, result.getHttpStatus());

        verify(customerServiceClient, times(1)).logout(any(TokenInvalidateRequest.class));

    }

    @Test
    public void shouldLogout_WhenInvalidTokenInvalidateRequest_ReturnsErrorResponse() {

        TokenInvalidateRequest tokenInvalidateRequest = TokenInvalidateRequestFixtures.getTokenInvalidateRequest();
        CustomResponse<Void> errorResponse = CustomResponse.<Void>builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .isSuccess(false)
                .build();

        when(customerServiceClient.logout(any(TokenInvalidateRequest.class)))
                .thenReturn(errorResponse);

        CustomResponse<Void> result = logoutService.logout(tokenInvalidateRequest);

        assertNotNull(result);
        assertFalse(result.getIsSuccess());
        assertEquals(HttpStatus.UNAUTHORIZED, result.getHttpStatus());

        verify(customerServiceClient, times(1)).logout(any(TokenInvalidateRequest.class));
    }

}