package com.testingpractice.duoclonebackend.testutils;

import java.sql.Timestamp
import java.time.LocalDate

object TestConstants {

        // ---- UNITS ----
        const val UNIT_1_TITLE = "Discuss a new Job"
    const val UNIT_2_TITLE = "Talk about your habits"
    const val UNIT_3_TITLE = "Pack for a vacation"
    const val UNIT_4_TITLE = "Plan Dinner"
    const val UNIT_5_TITLE = "Use complex verbs"
    const val UNIT_6_TITLE = "Argue with a roommate"

// ---- LESSONS ----
    const val LESSON_1_TITLE = "T1"
    const val LESSON_2_TITLE = "T2"
    const val LESSON_3_TITLE = "T3"
    const val LESSON_4_TITLE = "T4"
    const val LESSON_5_TITLE = "T5"
    const val LESSON_6_TITLE = "T6"

// ---- COURSES ----
    const val COURSE_1_ID = 1
    const val COURSE_2_ID = 2

// ---- SECTIONS ----
    const val SECTION_1_ID = 1
    const val SECTION_2_ID = 2

// ---- FIXED DATES (JANUARY 2025) ----
val FIXED_DATE_1: LocalDate = LocalDate.of(2025, 1, 1)
val FIXED_DATE_2: LocalDate = LocalDate.of(2025, 1, 2)
val FIXED_DATE_3: LocalDate = LocalDate.of(2025, 1, 3)

// ---- FIXED DATES (FEBRUARY 2025) ----
val FIXED_DATE_2_1: LocalDate = LocalDate.of(2025, 2, 1)
val FIXED_DATE_2_2: LocalDate = LocalDate.of(2025, 2, 2)
val FIXED_DATE_2_3: LocalDate = LocalDate.of(2025, 2, 3)

// ---- FIXED TIMESTAMPS (JANUARY) ----
val FIXED_TIMESTAMP_1: Timestamp =
        Timestamp.valueOf(FIXED_DATE_1.atStartOfDay())

val FIXED_TIMESTAMP_2: Timestamp =
        Timestamp.valueOf(FIXED_DATE_2.atStartOfDay())

val FIXED_TIMESTAMP_3: Timestamp =
        Timestamp.valueOf(FIXED_DATE_3.atStartOfDay())

// ---- FIXED TIMESTAMPS (FEBRUARY) ----
val FIXED_TIMESTAMP_2_1: Timestamp =
        Timestamp.valueOf(FIXED_DATE_2_1.atStartOfDay())

val FIXED_TIMESTAMP_2_2: Timestamp =
        Timestamp.valueOf(FIXED_DATE_2_2.atStartOfDay())

val FIXED_TIMESTAMP_2_3: Timestamp =
        Timestamp.valueOf(FIXED_DATE_2_3.atStartOfDay())
        }