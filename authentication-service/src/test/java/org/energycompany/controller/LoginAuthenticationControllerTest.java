package org.energycompany.controller;

import org.energycompany.common.BaseIntegrationTest;
import org.energycompany.fixtures.LoginRequestFixtures;
import org.energycompany.fixtures.TokenResponseFixtures;
import org.energycompany.model.CustomResponse;
import org.energycompany.model.auth.dto.request.LoginRequest;
import org.energycompany.model.auth.dto.response.TokenResponse;
import org.energycompany.service.CustomerLoginService;
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

class LoginAuthenticationControllerTest extends BaseIntegrationTest {

    @MockitoBean
    private CustomerLoginService customerLoginService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoginCustomer() throws Exception {

        LoginRequest loginRequest = LoginRequestFixtures.getLoginRequest();
        TokenResponse tokenResponse = TokenResponseFixtures.getTokenResponse();

        CustomResponse<TokenResponse> expectedResponse = CustomResponse.successOf(tokenResponse);
        when(customerLoginService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/authentication/customers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.response.accessToken").value("newAccessToken"));

        verify(customerLoginService, times(1)).login(any(LoginRequest.class));
    }
}
