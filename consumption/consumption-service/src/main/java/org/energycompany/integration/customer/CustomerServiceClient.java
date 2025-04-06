package org.energycompany.integration.customer;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "customer-service",
        url = "${integration.customer-service.url}"
)
@Headers({"Content-Type: application/json"})
public interface CustomerServiceClient {

    @PostMapping("/api/v1/customers/validate-token")
    void validateToken(@RequestParam(name = "token") String token);

    @GetMapping("/api/v1/customers/authenticate")
    UsernamePasswordAuthenticationToken getAuthentication(
            @RequestParam(name = "token") String token
    );
}
