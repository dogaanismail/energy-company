package org.energycompany.enums;

import lombok.Getter;

@Getter
public enum ResolutionEnum {
    HOUR("one_hour"),
    DAY("one_day"),
    WEEK("one_week"),
    MONTH("one_month"),
    YEAR("one_year");

    private final String resolutionValue;

    ResolutionEnum(String value) {
        this.resolutionValue = value;
    }
}
