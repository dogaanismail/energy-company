package org.energycompany.model.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.energycompany.model.auth.enums.CustomerStatus;
import org.energycompany.model.auth.enums.CustomerType;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Customer {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private CustomerStatus customerStatus;
    private CustomerType customerType;
    private String password;
}
