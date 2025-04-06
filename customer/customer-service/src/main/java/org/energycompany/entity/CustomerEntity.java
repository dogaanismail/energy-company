package org.energycompany.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.energycompany.model.auth.enums.CustomerStatus;
import org.energycompany.model.auth.enums.CustomerType;
import org.energycompany.model.customer.enums.TokenClaims;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
@Table(name = "customer")
public class CustomerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus = CustomerStatus.ACTIVE;

    public Map<String, Object> getClaims() {

        final Map<String, Object> claims = new HashMap<>();

        claims.put(TokenClaims.CUSTOMER_ID.getValue(), this.id);
        claims.put(TokenClaims.CUSTOMER_TYPE.getValue(), this.customerType);
        claims.put(TokenClaims.CUSTOMER_STATUS.getValue(), this.customerStatus);
        claims.put(TokenClaims.CUSTOMER_FIRST_NAME.getValue(), this.firstName);
        claims.put(TokenClaims.CUSTOMER_LAST_NAME.getValue(), this.lastName);
        claims.put(TokenClaims.CUSTOMER_EMAIL.getValue(), this.email);
        claims.put(TokenClaims.CUSTOMER_USERNAME.getValue(), this.userName);

        return claims;
    }

}
