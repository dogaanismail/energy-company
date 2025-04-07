package org.energycompany.integration.customer;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "customer-service",
        path = "/api/v1/customers"
)
@Headers({"Content-Type: application/json"})
public interface CustomerServiceClient {

    @PostMapping("/validate-token")
    void validateToken(@RequestParam(name = "token") String token);
}

