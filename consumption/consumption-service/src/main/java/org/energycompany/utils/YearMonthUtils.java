package org.energycompany.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

@UtilityClass
@Slf4j
public class YearMonthUtils {

    public static YearMonth getYearMonthFromDate(Instant dateTime) {

        try {
            return YearMonth.from(dateTime.atZone(ZoneId.of("Europe/Tallinn")));
        } catch (DateTimeException | ZoneRulesException e) {
            log.error("Error getting year month from date", e);
            return null;
        }
    }
}
