package org.energycompany.model.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @Email(message = "Please enter valid e-mail address")
    @Size(min = 7, message = "Minimum e-mail length is 7 characters.")
    private String email;

    @Size(min = 8)
    private String password;

    @NotBlank(message = "First name can't be blank.")
    private String firstName;

    @NotBlank(message = "Last name can't be blank.")
    private String lastName;

    @NotBlank(message = "Role can't be blank.")
    private String role;

}
