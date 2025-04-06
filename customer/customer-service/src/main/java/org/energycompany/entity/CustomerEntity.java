package org.energycompany.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.energycompany.model.auth.enums.CustomerStatus;
import org.energycompany.model.auth.enums.CustomerType;
import org.energycompany.enums.TokenClaims;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private UUID id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "customer_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Column(name = "customer_status", nullable = false, length = 50)
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

        return claims;
    }

}
