package org.energycompany.controller;

import org.energycompany.common.BaseIntegrationTest;
import org.energycompany.fixtures.TokenRefreshRequestFixtures;
import org.energycompany.fixtures.TokenResponseFixtures;
import org.energycompany.model.CustomResponse;
import org.energycompany.model.auth.dto.request.TokenRefreshRequest;
import org.energycompany.model.auth.dto.response.TokenResponse;
import org.energycompany.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RefreshTokenAuthenticationControllerTest extends BaseIntegrationTest {

    @MockitoBean
    private RefreshTokenService refreshTokenService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRefreshToken() throws Exception {

        TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequestFixtures.getTokenRefreshRequest();
        TokenResponse tokenResponse = TokenResponseFixtures.getTokenResponse();

        CustomResponse<TokenResponse> expectedResponse = CustomResponse.successOf(tokenResponse);

        when(refreshTokenService.refreshToken(any(TokenRefreshRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/authentication/customers/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenRefreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.response.accessToken").value("newAccessToken"));

        verify(refreshTokenService, times(1)).refreshToken(any(TokenRefreshRequest.class));
    }

}
