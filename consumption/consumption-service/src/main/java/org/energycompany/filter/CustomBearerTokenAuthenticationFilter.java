package org.energycompany.filter;

import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.energycompany.model.Token;
import org.energycompany.service.GetAuthenticationService;
import org.energycompany.service.ValidateTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomBearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private final GetAuthenticationService getAuthenticationService;
    private final ValidateTokenService validateTokenService;

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest httpServletRequest,
                                    @NonNull final HttpServletResponse httpServletResponse,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {

        log.debug("CustomBearerTokenAuthenticationFilter: Request received for URI: {}", httpServletRequest.getRequestURI());

        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (Token.isBearerToken(authorizationHeader)) {
            final String jwtToken = Token.getJwt(authorizationHeader);

            try {

                // Validate the token synchronously
                validateTokenService.validateToken(jwtToken);
                log.debug("Token validation succeeded for request: {}", httpServletRequest.getRequestURI());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthenticationService
                        .getUsernamePasswordAuthenticationToken(jwtToken);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } catch (FeignException e) {
                log.error("Token validation failed for request: {}", httpServletRequest.getRequestURI(), e);

                // Handle the error response
                if (e instanceof FeignException.Unauthorized || e instanceof FeignException.Forbidden) {
                    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                } else {
                    httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
                httpServletResponse.getWriter().write(e.getMessage());
            }
        } else {
            log.warn("Missing or invalid Authorization header for request: {}", httpServletRequest.getRequestURI());
        }

        // Proceed with the filter chain in any case
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}