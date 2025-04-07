package org.energycompany.filter;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energycompany.integration.customer.CustomerServiceClient;
import org.energycompany.model.Token;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    public static class Config {
        private List<String> publicEndpoints;

        public List<String> getPublicEndpoints() {
            return publicEndpoints;
        }

        public Config setPublicEndpoints(List<String> publicEndpoints) {
            this.publicEndpoints = publicEndpoints;
            return this;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (config != null && config.getPublicEndpoints().stream().anyMatch(path::startsWith)) {
                return chain.filter(exchange);
            }

            String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {

                log.warn("Missing Authorization header for path: {}", path);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            ApplicationContext context = exchange.getApplicationContext();
            if (context == null) {

                log.error("ApplicationContext is null");
                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                return exchange.getResponse().setComplete();
            }

            CustomerServiceClient customerServiceClient = context.getBean(CustomerServiceClient.class);
            if (Token.isBearerToken(authorizationHeader)) {

                String jwtToken = Token.getJwt(authorizationHeader);
                return Mono.fromCallable(() -> {
                            customerServiceClient.validateToken(jwtToken);
                            log.debug("Token validation succeeded for path: {}", path);
                            return true;
                        })
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(valid -> chain.filter(exchange))
                        .onErrorResume(e -> {
                            log.error("Token validation failed for path: {}", path, e);
                            if (e instanceof FeignException.Unauthorized || e instanceof FeignException.Forbidden) {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            } else {
                                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                            return exchange.getResponse().setComplete();
                        });
            }

            log.warn("Missing or invalid Authorization header for path: {}", path);
            return chain.filter(exchange);
        };
    }

}