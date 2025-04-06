package org.energycompany.fixtures;

import org.energycompany.model.auth.dto.response.TokenResponse;

public class TokenResponseFixtures {

    public static TokenResponse getTokenResponse() {

        return TokenResponse.builder()
                .accessToken("newAccessToken")
                .accessTokenExpiresAt(System.currentTimeMillis() + 3600)
                .refreshToken("newRefreshToken")
                .build();
    }
}
