package org.energycompany.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.model.CustomResponse;
import org.energycompany.model.Token;
import org.energycompany.model.auth.Customer;
import org.energycompany.model.auth.dto.request.LoginRequest;
import org.energycompany.model.auth.dto.request.RegisterRequest;
import org.energycompany.model.auth.dto.request.TokenInvalidateRequest;
import org.energycompany.model.auth.dto.request.TokenRefreshRequest;
import org.energycompany.model.auth.dto.response.TokenResponse;
import org.energycompany.model.customer.mapper.TokenToTokenResponseMapper;
import org.energycompany.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerLoginService customerLoginService;
    private final CustomerService customerService;
    private final LogoutService logoutService;
    private final RefreshTokenService refreshTokenService;
    private final RegisterService registerService;
    private final TokenService tokenService;
    private final TokenToTokenResponseMapper tokenToTokenResponseMapper = TokenToTokenResponseMapper
            .initialize();

    @PostMapping("/register")
    public CustomResponse<Void> registerCustomer(
            @RequestBody @Validated RegisterRequest registerRequest) {

        log.info("Received a request to register a new customer");
        registerService.registerCustomer(registerRequest);
        return CustomResponse.SUCCESS;
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Void> validateToken(
            @RequestParam(name = "token") String token) {

        log.info("Received a request to validate a token");
        tokenService.verifyAndValidate(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginCustomer(
            @RequestBody @Valid LoginRequest loginRequest) {

        log.info("Received a request to login a customer");
        Token token = customerLoginService.login(loginRequest);
        TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(
            @RequestBody @Valid TokenRefreshRequest tokenRefreshRequest) {

        log.info("Received a request to refresh a customer's token");
        Token token = refreshTokenService.refreshToken(tokenRefreshRequest);
        TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/logout")
    public CustomResponse<Void> logout(
            @RequestBody @Valid TokenInvalidateRequest tokenInvalidateRequest) {

        log.info("Received a request to logout a customer");
        logoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<UsernamePasswordAuthenticationToken> getAuthentication(
            @RequestParam(name = "token") String token) {

        log.info("Received a request to get authentication for a token");
        UsernamePasswordAuthenticationToken authentication = tokenService.getAuthentication(token);
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/customer")
    public CustomResponse<Customer> getCustomer(
            @RequestParam(name = "customerId") UUID customerId) {

        log.info("Received a request to get customer by id, {}", customerId);
        Customer customer = customerService.getCustomer(customerId);
        return CustomResponse.successOf(customer);
    }

}
