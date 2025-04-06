package org.energycompany.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.model.auth.dto.request.LoginRequest;
import org.energycompany.model.auth.dto.request.RegisterRequest;
import org.energycompany.model.auth.dto.request.TokenInvalidateRequest;
import org.energycompany.model.auth.dto.request.TokenRefreshRequest;
import org.energycompany.model.auth.dto.response.TokenResponse;
import org.energycompany.model.CustomResponse;
import org.energycompany.service.CustomerLoginService;
import org.energycompany.service.LogoutService;
import org.energycompany.service.RefreshTokenService;
import org.energycompany.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication/customers")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final RegisterService registerService;
    private final CustomerLoginService customerLoginService;
    private final RefreshTokenService refreshTokenService;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public CustomResponse<Void> registerCustomer(@RequestBody @Valid RegisterRequest registerRequest) {

        log.info("Received a request to register a new customer");
        registerService.registerCustomer(registerRequest);
        return CustomResponse.SUCCESS;
    }


    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginCustomer(@RequestBody @Valid LoginRequest loginRequest) {

        log.info("Received a request to login a customer");
        return customerLoginService.login(loginRequest);
    }


    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid TokenRefreshRequest tokenRefreshRequest) {

        log.info("Received a request to refresh a customer's token");
        return refreshTokenService.refreshToken(tokenRefreshRequest);
    }


    @PostMapping("/logout")
    public CustomResponse<Void> logout(@RequestBody @Valid TokenInvalidateRequest tokenInvalidateRequest) {

        log.info("Received a request to logout a customer");
        logoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }
}