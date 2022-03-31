package com.breckner.happyshop.domain.service;

import java.time.Clock;
import java.time.ZonedDateTime;

public class DateTimeHelper {

    private static Clock clock = Clock.systemDefaultZone();

    public static ZonedDateTime now() {
        return ZonedDateTime.now(clock);
    }

    public static void setClock(Clock clock) {
        DateTimeHelper.clock = clock;
    }
}
