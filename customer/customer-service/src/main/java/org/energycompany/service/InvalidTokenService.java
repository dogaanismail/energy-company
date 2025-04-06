package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.entity.InvalidTokenEntity;
import org.energycompany.exception.TokenAlreadyInvalidatedException;
import org.energycompany.repository.InvalidTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvalidTokenService {

    private final InvalidTokenRepository invalidTokenRepository;

    public void invalidateTokens(Set<String> tokenIds) {

        Set<InvalidTokenEntity> invalidTokenEntities = tokenIds.stream()
                .map(tokenId -> InvalidTokenEntity.builder()
                        .tokenId(tokenId)
                        .build()
                )
                .collect(Collectors.toSet());

        invalidTokenRepository.saveAll(invalidTokenEntities);
    }

    public void checkForInvalidityOfToken(String tokenId) {

        boolean isTokenInvalid = invalidTokenRepository
                .findByTokenId(tokenId)
                .isPresent();

        if (isTokenInvalid) {
            throw new TokenAlreadyInvalidatedException(tokenId);
        }
    }

}
