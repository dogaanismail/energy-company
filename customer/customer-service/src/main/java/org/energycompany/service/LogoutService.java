package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.model.auth.dto.request.TokenInvalidateRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final TokenService tokenService;
    private final InvalidTokenService invalidTokenService;

    public void logout(TokenInvalidateRequest tokenInvalidateRequest) {

        tokenService.verifyAndValidate(
                Set.of(
                        tokenInvalidateRequest.getAccessToken(),
                        tokenInvalidateRequest.getRefreshToken()
                )
        );

        String accessTokenId = tokenService
                .getPayload(tokenInvalidateRequest.getAccessToken())
                .getId();

        invalidTokenService.checkForInvalidityOfToken(accessTokenId);

        String refreshTokenId = tokenService
                .getPayload(tokenInvalidateRequest.getRefreshToken())
                .getId();

        invalidTokenService.checkForInvalidityOfToken(refreshTokenId);

        invalidTokenService.invalidateTokens(Set.of(accessTokenId, refreshTokenId));
    }

}
