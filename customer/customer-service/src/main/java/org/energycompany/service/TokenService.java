package org.energycompany.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.energycompany.config.TokenConfigurationParameter;
import org.energycompany.model.Token;
import org.energycompany.model.auth.enums.CustomerType;
import org.energycompany.enums.TokenClaims;
import org.energycompany.enums.TokenType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final TokenConfigurationParameter tokenConfigurationParameter;
    private final InvalidTokenService invalidTokenService;

    public void verifyAndValidate(Set<String> jwts) {
        jwts.forEach(this::verifyAndValidate);
    }

    public Token generateToken(Map<String, Object> claims) {

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date accessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis),
                tokenConfigurationParameter.getAccessTokenExpireMinute()
        );

        String accessToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claims(claims)
                .compact();

        Date refreshTokenExpiresAt = DateUtils.addDays(
                new Date(currentTimeMillis),
                tokenConfigurationParameter.getRefreshTokenExpireDay()
        );

        String refreshToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claim(TokenClaims.CUSTOMER_ID.getValue(), claims.get(TokenClaims.CUSTOMER_ID.getValue()))
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }


    public Token generateToken(
            Map<String, Object> claims,
            String refreshToken) {

        long currentTimeMillis = System.currentTimeMillis();

        String refreshTokenId = this.getId(refreshToken);

        invalidTokenService.checkForInvalidityOfToken(refreshTokenId);

        Date accessTokenIssuedAt = new Date(currentTimeMillis);

        Date accessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis),
                tokenConfigurationParameter.getAccessTokenExpireMinute()
        );

        String accessToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuedAt(accessTokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claims(claims)
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(token);

        JwsHeader jwsHeader = claimsJws.getHeader();
        Claims payload = claimsJws.getPayload();

        Jwt jwt = new Jwt(
                token,
                payload.getIssuedAt().toInstant(),
                payload.getExpiration().toInstant(),
                Map.of(
                        TokenClaims.TYP.getValue(), jwsHeader.getType(),
                        TokenClaims.ALGORITHM.getValue(), jwsHeader.getAlgorithm()
                ),
                payload
        );

        CustomerType customerType = CustomerType.valueOf(payload.get(TokenClaims.CUSTOMER_TYPE.getValue()).toString());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(customerType.name()));

        return UsernamePasswordAuthenticationToken.authenticated(
                jwt,
                null,
                authorities);
    }

    public void verifyAndValidate(String jwt) {

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(tokenConfigurationParameter.getPublicKey())
                    .build()
                    .parseSignedClaims(jwt);

            // Log the claims for debugging purposes
            Claims claims = claimsJws.getPayload();
            log.info("Token claims: {}", claims);

            // Additional checks (e.g., expiration, issuer, etc.)
            if (claims.getExpiration().before(new Date())) {
                throw new JwtException("Token has expired");
            }

            log.info("Token is valid");
        } catch (ExpiredJwtException e) {
            log.error("Token has expired", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired", e);
        } catch (JwtException e) {
            log.error("Invalid JWT token", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token", e);
        } catch (Exception e) {
            log.error("Error validating token", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error validating token", e);
        }
    }

    public Claims getPayload(String jwt) {

        return Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public String getId(String jwt) {

        return Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getId();
    }

}
