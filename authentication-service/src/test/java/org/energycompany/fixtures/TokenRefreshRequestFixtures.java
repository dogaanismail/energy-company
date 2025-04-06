package org.energycompany.fixtures;

import org.energycompany.model.auth.dto.request.TokenRefreshRequest;

public class TokenRefreshRequestFixtures {

    public static TokenRefreshRequest getTokenRefreshRequest() {

        return TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();
    }

    public static TokenRefreshRequest getInvalidTokenRefreshRequest() {

        return TokenRefreshRequest.builder()
                .refreshToken("invalidRefreshToken")
                .build();
    }
}
