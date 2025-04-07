package org.energycompany.factory;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@UtilityClass
public class YearFactory {

    public static Instant getYearStartDateTime(int year) {

        return LocalDate.of(year, 1, 1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();
    }

    public static Instant getYearEndDateTime(int year) {

        return LocalDate.of(year, 12, 31)
                .atTime(LocalTime.MAX)
                .atZone(ZoneOffset.UTC)
                .toInstant();
    }
}
