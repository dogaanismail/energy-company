package org.energycompany.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.energycompany.enums.TokenClaims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Optional;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "deleted_at", updatable = false)
    private Instant deletedAt;

    @Column(name = "deleted_by", updatable = false)
    private String deletedBy;

    @Column(name = "deleted_reason")
    private String deletedReason;

    @Version
    @Column(
            name = "version",
            nullable = false,
            columnDefinition = "integer default 0"
    )
    private Integer version;

    @PrePersist
    public void prePersist() {

        this.createdBy = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !"anonymousUser".equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.CUSTOMER_EMAIL.getValue()).toString())
                .orElse("anonymousUser");

        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {

        this.updatedBy = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !"anonymousUser".equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.CUSTOMER_EMAIL.getValue()).toString())
                .orElse("anonymousUser");

        this.updatedAt = Instant.now();
    }

    @PreRemove
    public void preRemove() {

        this.deletedBy = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !"anonymousUser".equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(TokenClaims.CUSTOMER_EMAIL.getValue()).toString())
                .orElse("anonymousUser");

        this.deletedAt = Instant.now();
    }

}
