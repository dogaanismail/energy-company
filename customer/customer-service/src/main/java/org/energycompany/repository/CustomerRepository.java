package org.energycompany.repository;

import org.energycompany.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    boolean existsCustomerEntityByEmail(final String email);

    Optional<CustomerEntity> findCustomerEntityByEmail(final String email);
}
