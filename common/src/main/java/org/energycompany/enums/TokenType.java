package org.energycompany.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {

    BEARER("Bearer");

    private final String value;

}
