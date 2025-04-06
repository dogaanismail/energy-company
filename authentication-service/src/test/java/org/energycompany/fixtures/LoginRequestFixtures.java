package org.energycompany.fixtures;

import lombok.experimental.UtilityClass;
import org.energycompany.model.auth.dto.request.LoginRequest;

@UtilityClass
public class LoginRequestFixtures {

    public static LoginRequest getLoginRequest() {

        return LoginRequest.builder()
                .email("sample username")
                .password("validPassword123")
                .build();
    }
}
