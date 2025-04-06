package org.energycompany.service;

import org.energycompany.common.AbstractBaseServiceTest;
import org.energycompany.fixtures.TokenRefreshRequestFixtures;
import org.energycompany.fixtures.TokenResponseFixtures;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.energycompany.model.CustomResponse;
import org.energycompany.model.auth.dto.request.TokenRefreshRequest;
import org.energycompany.model.auth.dto.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RefreshTokenServiceTest extends AbstractBaseServiceTest {

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Mock
    private CustomerServiceClient customerServiceClient;

    @Test
    void shouldRefreshToken_WhenValidTokenRefreshRequest_ReturnsSuccess() {

        TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequestFixtures.getTokenRefreshRequest();
        TokenResponse tokenResponse = TokenResponseFixtures.getTokenResponse();

        CustomResponse<TokenResponse> expectedResponse = CustomResponse.successOf(tokenResponse);

        when(customerServiceClient.refreshToken(any(TokenRefreshRequest.class)))
                .thenReturn(expectedResponse);

        CustomResponse<TokenResponse> result = refreshTokenService.refreshToken(tokenRefreshRequest);

        assertNotNull(result);
        assertTrue(result.getIsSuccess());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(tokenResponse, result.getResponse());

        verify(customerServiceClient, times(1)).refreshToken(any(TokenRefreshRequest.class));
    }

    @Test
    void shouldRefreshToken_WhenInvalidTokenRefreshRequest_ReturnsErrorResponse() {

        TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken("invalidRefreshToken")
                .build();

        CustomResponse<TokenResponse> errorResponse = CustomResponse.<TokenResponse>builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .isSuccess(false)
                .build();

        when(customerServiceClient.refreshToken(any(TokenRefreshRequest.class)))
                .thenReturn(errorResponse);

        CustomResponse<TokenResponse> result = refreshTokenService.refreshToken(tokenRefreshRequest);

        assertNotNull(result);
        assertFalse(result.getIsSuccess());
        assertEquals(HttpStatus.UNAUTHORIZED, result.getHttpStatus());
        assertNull(result.getResponse());

        verify(customerServiceClient, times(1)).refreshToken(any(TokenRefreshRequest.class));
    }

}