package org.energycompany.fixtures;

import lombok.experimental.UtilityClass;
import org.energycompany.model.auth.dto.request.RegisterRequest;

@UtilityClass
public class RegisterRequestFixtures {

    public static RegisterRequest getRegisterRequest() {

        return RegisterRequest.builder()
                .email("valid.email@example.com")
                .password("validPassword123")
                .firstName("John")
                .lastName("Doe")
                .userName("sample-username")
                .role("customer")
                .build();
    }

    public static RegisterRequest getRegisterRequestWithInvalidEmail() {

        return RegisterRequest.builder()
                .email("invalid.email")
                .password("short")
                .firstName("")
                .lastName("")
                .userName("sample-username")
                .role("")
                .build();
    }
}
