package com.testingpractice.duoclonebackend.controller

import io.restassured.RestAssured.given
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_1_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_2_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_3_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_4_TITLE
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeLesson

class UnitControllerIT : AbstractIntegrationTest() {

    @BeforeEach
    fun seed() {
        lessonRepository.deleteAll()

        lessonRepository.saveAll(
            listOf(
                makeLesson(LESSON_1_TITLE, 2, 1, "Exercise"),
                makeLesson(LESSON_2_TITLE, 2, 2, "Exercise"),
                makeLesson(LESSON_3_TITLE, 2, 3, "Exercise"),
                makeLesson(LESSON_4_TITLE, 1, 3, "Exercise")
            )
        )
    }

    @Test
    fun getLessonsByUnit_returnsLessonsForThatUnit() {
        given()
            .`when`()
            .get(pathConstants.UNITS + pathConstants.GET_LESSONS_BY_UNIT, 2)
            .then()
            .statusCode(200)
            .body("$", hasSize<Any>(3))
            .body(
                "title",
                containsInAnyOrder(
                    LESSON_1_TITLE,
                    LESSON_2_TITLE,
                    LESSON_3_TITLE
                )
            )
            .body("[0].id", notNullValue())
            .body("[1].unitId", notNullValue())
    }
}