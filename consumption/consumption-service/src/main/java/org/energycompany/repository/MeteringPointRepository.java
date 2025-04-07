package org.energycompany.repository;

import org.energycompany.entity.MeteringPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeteringPointRepository extends JpaRepository<MeteringPoint, UUID> {

    List<MeteringPoint> getMeteringPointsByCustomerId(UUID customerId);

    MeteringPoint getMeteringPointById(UUID meteringPointId);
}
