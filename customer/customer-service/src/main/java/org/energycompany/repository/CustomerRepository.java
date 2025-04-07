package org.energycompany.repository;

import org.energycompany.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    boolean existsCustomerEntityByEmail(final String email);

    Optional<CustomerEntity> findCustomerEntityByEmail(final String email);
}
