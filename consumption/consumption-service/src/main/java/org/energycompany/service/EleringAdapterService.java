package org.energycompany.service;

import lombok.RequiredArgsConstructor;
import org.energycompany.integration.elering.EleringAdapterClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EleringAdapterService {

    private final EleringAdapterClient eleringAdapterClient;
}
