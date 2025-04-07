package org.energycompany.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.YearMonth;

class YearMonthUtilsTest {

    @Test
    void shouldReturnYearMonthFromDate() {

        Instant dateTime = Instant.parse("2025-04-09T00:00:00+03:00");
        YearMonth yearMonth = YearMonthUtils.getYearMonthFromDate(dateTime);

        Assertions.assertNotNull(yearMonth);
        Assertions.assertEquals(2025, yearMonth.getYear());
        Assertions.assertEquals(4, yearMonth.getMonthValue());
    }
}