package org.energycompany.controller;

import org.energycompany.common.BaseIntegrationTest;
import org.energycompany.fixtures.TokenInvalidateRequestFixtures;
import org.energycompany.model.auth.dto.request.TokenInvalidateRequest;
import org.energycompany.service.LogoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LogoutAuthenticationControllerTest extends BaseIntegrationTest {

    @MockitoBean
    private LogoutService logoutService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCustomerLogout() throws Exception {

        TokenInvalidateRequest tokenInvalidateRequest = TokenInvalidateRequestFixtures.getTokenInvalidateRequest();
        mockMvc.perform(post("/api/v1/authentication/customers/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenInvalidateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.httpStatus").value("OK"));

        verify(logoutService, times(1)).logout(any(TokenInvalidateRequest.class));

    }
}
