package org.energycompany.fixtures;

import org.energycompany.model.auth.dto.request.TokenInvalidateRequest;

public class TokenInvalidateRequestFixtures {

    public static TokenInvalidateRequest getTokenInvalidateRequest() {

        return TokenInvalidateRequest.builder()
                .accessToken("validAccessToken")
                .refreshToken("validRefreshToken")
                .build();
    }
}
