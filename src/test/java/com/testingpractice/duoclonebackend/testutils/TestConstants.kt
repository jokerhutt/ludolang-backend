package com.testingpractice.duoclonebackend.testutils;

import java.sql.Timestamp;
import java.time.LocalDate;

public class TestConstants {

  // ----UNITS----
  public static String UNIT_1_TITLE = "Discuss a new Job";
  public static String UNIT_2_TITLE = "Talk about your habits";
  public static String UNIT_3_TITLE = "Pack for a vacation";
  public static String UNIT_4_TITLE = "Plan Dinner";
  public static String UNIT_5_TITLE = "Use complex verbs";
  public static String UNIT_6_TITLE = "Argue with a roommate";

  // ----LESSONS----
  public static String LESSON_1_TITLE = "T1";
  public static String LESSON_2_TITLE = "T2";
  public static String LESSON_3_TITLE = "T3";
  public static String LESSON_4_TITLE = "T4";
  public static String LESSON_5_TITLE = "T5";
  public static String LESSON_6_TITLE = "T6";

  // ----COURSES----
  public static Integer COURSE_1_ID = 1;
  public static Integer COURSE_2_ID = 2;

  // ----SECTIONS----
  public static Integer SECTION_1_ID = 1;
  public static Integer SECTION_2_ID = 2;

  // For month 1 (JANUARY 2025)
  public static final LocalDate FIXED_DATE_1 = LocalDate.of(2025, 1, 1);
  public static final LocalDate FIXED_DATE_2 = LocalDate.of(2025, 1, 2);
  public static final LocalDate FIXED_DATE_3 = LocalDate.of(2025, 1, 3);

  // For month 2 (FEBRUARY 2025)
  public static final LocalDate FIXED_DATE_2_1 = LocalDate.of(2025, 2, 1);
  public static final LocalDate FIXED_DATE_2_2 = LocalDate.of(2025, 2, 2);
  public static final LocalDate FIXED_DATE_2_3 = LocalDate.of(2025, 2, 3);

  // First 3 days of January 2025 at start of day
  public static final Timestamp FIXED_TIMESTAMP_1 =
          Timestamp.valueOf(FIXED_DATE_1.atStartOfDay());
  public static final Timestamp FIXED_TIMESTAMP_2 =
          Timestamp.valueOf(FIXED_DATE_2.atStartOfDay());
  public static final Timestamp FIXED_TIMESTAMP_3 =
          Timestamp.valueOf(FIXED_DATE_3.atStartOfDay());

  // First 3 days of February 2025 at start of day
  public static final Timestamp FIXED_TIMESTAMP_2_1 =
          Timestamp.valueOf(FIXED_DATE_2_1.atStartOfDay());
  public static final Timestamp FIXED_TIMESTAMP_2_2 =
          Timestamp.valueOf(FIXED_DATE_2_2.atStartOfDay());
  public static final Timestamp FIXED_TIMESTAMP_2_3 =
          Timestamp.valueOf(FIXED_DATE_2_3.atStartOfDay());

}
