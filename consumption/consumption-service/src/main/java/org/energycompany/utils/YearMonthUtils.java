package org.energycompany.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

@UtilityClass
public class YearMonthUtils {

    public static YearMonth getYearMonthFromDate(Instant dateTime) {

        return YearMonth.from(dateTime.atZone(ZoneId.of("Europe/Tallinn")));
    }

}
