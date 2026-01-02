package com.testingpractice.duoclonebackend.utils;

import java.sql.Timestamp
import java.time.Clock
import java.time.Instant
import java.time.LocalDate

object DateUtils {

fun today(clock: Clock): LocalDate =
        LocalDate.now(clock)

fun nowTs(clock: Clock): Timestamp =
        Timestamp.from(Instant.now(clock))
        }