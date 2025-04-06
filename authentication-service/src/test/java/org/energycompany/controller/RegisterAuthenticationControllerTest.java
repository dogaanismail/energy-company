package org.energycompany.controller;

import org.energycompany.common.BaseIntegrationTest;
import org.energycompany.fixtures.RegisterRequestFixtures;
import org.energycompany.model.auth.dto.request.RegisterRequest;
import org.energycompany.service.RegisterService;
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

class RegisterAuthenticationControllerTest extends BaseIntegrationTest {

    @MockitoBean
    private RegisterService registerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldInvokeRegisterCustomerService() throws Exception {

        RegisterRequest registerRequest = RegisterRequestFixtures.getRegisterRequest();
        mockMvc.perform(post("/api/v1/authentication/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.httpStatus").value("OK"));

        verify(registerService, times(1)).registerCustomer(any(RegisterRequest.class));
    }
}
