package org.energycompany.config;

import lombok.RequiredArgsConstructor;
import org.energycompany.constants.EurekaServerServices;
import org.energycompany.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/v1/authentication/customers/register",
            "/api/v1/authentication/customers/login",
            "/api/v1/authentication/customers/refresh-token",
            "/api/v1/authentication/customers/logout",
            "/actuator/**"
    );

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(EurekaServerServices.CONSUMPTION_SERVICE.getServiceName(), r -> r.path(
                                "/api/v1/consumptions/**"
                        )
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://consumption-service"))
                .route(EurekaServerServices.AUTHENTICATION_SERVICE.getServiceName(), r -> r.path("/api/v1/authentication/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://authentication-service"))
                .route(EurekaServerServices.CUSTOMER_SERVICE.getServiceName(), r -> r.path("/api/v1/customers/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://customer-service"))
                .route(EurekaServerServices.ELERING_ADAPTER.getServiceName(), r -> r.path("/api/v1/electric-price/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://elering-adapter"))
                .build();
    }
}