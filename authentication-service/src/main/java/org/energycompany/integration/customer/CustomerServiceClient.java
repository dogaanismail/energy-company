package org.energycompany.integration.customer;

import feign.Headers;
import jakarta.validation.Valid;
import org.energycompany.model.auth.Customer;
import org.energycompany.model.auth.dto.request.LoginRequest;
import org.energycompany.model.auth.dto.request.RegisterRequest;
import org.energycompany.model.auth.dto.request.TokenInvalidateRequest;
import org.energycompany.model.auth.dto.request.TokenRefreshRequest;
import org.energycompany.model.auth.dto.response.TokenResponse;
import org.energycompany.model.CustomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "customer-service",
        url = "${integration.customer-service.url}"
)
@Headers({"Content-Type: application/json"})
public interface CustomerServiceClient {

    @PostMapping(value = "/api/v1/customers/validate-token")
    void validateToken(@RequestParam(name = "token") String token);

    @PostMapping(value = "/api/v1/customers/register")
    ResponseEntity<Customer> register(@RequestBody @Valid RegisterRequest request);

    @PostMapping(value = "/api/v1/customers/login")
    CustomResponse<TokenResponse> loginCustomer(@RequestBody @Valid LoginRequest loginRequest);

    @PostMapping(value = "/api/v1/customers//refresh-token")
    CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid TokenRefreshRequest tokenRefreshRequest);

    @PostMapping(value = "/api/v1/customers/logout")
    CustomResponse<Void> logout(@RequestBody @Valid TokenInvalidateRequest tokenInvalidateRequest);
}

