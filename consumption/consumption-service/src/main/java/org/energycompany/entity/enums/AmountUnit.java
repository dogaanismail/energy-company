package org.energycompany.entity.enums;

import lombok.Getter;

@Getter
public enum AmountUnit {

    kWH("kWH"),
    MWh("MWh");

    private final String amountUnit;

    AmountUnit(String amountUnit) {
        this.amountUnit = amountUnit;
    }
}
