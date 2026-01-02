package com.testingpractice.duoclonebackend.controller;

import static com.testingpractice.duoclonebackend.testutils.TestConstants.*;
import static com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_4_TITLE;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.makeLesson;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.testingpractice.duoclonebackend.commons.constants.pathConstants;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitControllerIT extends AbstractIntegrationTest {

  @BeforeEach
  void seed() {
    lessonRepository.deleteAll();

    lessonRepository.saveAll(
        List.of(
            makeLesson(LESSON_1_TITLE, 2, 1, "Exercise"),
            makeLesson(LESSON_2_TITLE, 2, 2, "Exercise"),
            makeLesson(LESSON_3_TITLE, 2, 3, "Exercise"),
            makeLesson(LESSON_4_TITLE, 1, 3, "Exercise")));
  }

  @Test
  void getLessonsByUnit_returnsLessonsForThatUnit() {
    given()
        .when()
        .get(pathConstants.UNITS + pathConstants.GET_LESSONS_BY_UNIT, 2)
        .then()
        .statusCode(200)
        .body("$", hasSize(3))
        .body("title", containsInAnyOrder(LESSON_1_TITLE, LESSON_2_TITLE, LESSON_3_TITLE))
        .body("[0].id", notNullValue())
        .body("[1].unitId", notNullValue());
  }
}
