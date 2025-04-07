package org.energycompany.repository;

import org.energycompany.entity.InvalidTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidTokenEntity, String> {

    Optional<InvalidTokenEntity> findByTokenId(String tokenId);
}
