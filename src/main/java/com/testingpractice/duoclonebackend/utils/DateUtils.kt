package com.testingpractice.duoclonebackend.utils;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public final class DateUtils {
    private DateUtils() {}
    public static LocalDate today(Clock clock) { return LocalDate.now(clock); }
    public static Timestamp nowTs(Clock clock) { return Timestamp.from(Instant.now(clock)); }
}