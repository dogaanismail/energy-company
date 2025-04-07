package org.energycompany.repository;

import org.energycompany.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, UUID> {

    List<Consumption> findByCustomerIdAndMeteringPointIdAndConsumptionTimeBetween(
            UUID customerId,
            UUID meteringPointId,
            Instant startDateTime,
            Instant endDateTime
    );
}
