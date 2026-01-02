package com.testingpractice.duoclonebackend.controller

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import com.testingpractice.duoclonebackend.progress.api.dto.LessonCompleteResponse
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_1
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_2
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeCourse
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeLesson
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeSection
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUnit
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUserCourseProgress
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LessonCompletionControllerIT : AbstractIntegrationTest() {

    @BeforeEach
    fun seed() {
    }

    @Test
    fun submitLesson_happyPath_endCourse_returnsCourseComplete() {

        val lessonId = l6.id!!
        val userId =
            setupUserCompletionForTest(
                3,
                course1.id!!,
                lessonId,
                l6.id!!,
                5,
                0,
                1,
                FIXED_TIMESTAMP_1,
                FIXED_TIMESTAMP_2
            )

        val response = submitLessonBody(userId, lessonId, course1.id!!)

        assertThat(response.updatedUserCourseProgress!!.isComplete).isTrue
        assertThat(response.message).isNotNull
        assertThat(response.updatedUserCourseProgress!!.currentLessonId == lessonId).isTrue
    }

    @Test
    fun submitLesson_happyPath_jumpAhead_returnsListToUpdate() {

        val lessonId = l5.id!!
        val userId =
            setupUserCompletionForTest(
                3,
                course1.id!!,
                lessonId,
                l3.id!!,
                2,
                0,
                1,
                FIXED_TIMESTAMP_1,
                FIXED_TIMESTAMP_2
            )
        val nextLessonId = l6.id!!

        val response = submitLessonBody(userId, lessonId, course1.id!!)

        assertThat(response.updatedUserCourseProgress!!.currentLessonId).isEqualTo(nextLessonId)
        assertThat(response.lessonsToUpdate.size).isEqualTo(2)
    }

    @Test
    fun submitLesson_courseMismatch_throwsException() {

        val course2 = courseRepository.save(makeCourse("Course 2", "Title 2"))
        val section2 = sectionRepository.save(makeSection("Section 2", course2.id!!, 1))
        val unit2 = unitRepository.save(makeUnit("Unit 2", course2.id!!, section2.id!!, 1))
        val l1_2 = lessonRepository.save(makeLesson("Lesson 1.2", unit2.id!!, 1, "CLOZE"))

        val lessonId = l5.id!!
        val userId =
            setupUserCompletionForTest(
                3,
                course2.id!!,
                lessonId,
                l1_2.id!!,
                4,
                40,
                1,
                FIXED_TIMESTAMP_1,
                FIXED_TIMESTAMP_2
            )

        userCourseProgressRepository.save(
            makeUserCourseProgress(
                userId,
                course1.id!!,
                false,
                l5.id!!,
                FIXED_TIMESTAMP_2
            )
        )

        given()
            .header("X-Test-User-Id", userId)
            .contentType("application/json")
            .body(
                mapOf(
                    "lessonId" to lessonId,
                    "sectionId" to course1.id!!
                )
            )
            .`when`()
            .post(pathConstants.LESSONS_COMPLETIONS + pathConstants.SUBMIT_COMPLETED_LESSON)
            .then()
            .statusCode(ErrorCode.COURSE_MISMATCH.status().value())
            .body("title", equalTo(ErrorCode.COURSE_MISMATCH.name))
            .body("detail", equalTo(ErrorCode.COURSE_MISMATCH.defaultMessage()))
            .body("status", equalTo(ErrorCode.COURSE_MISMATCH.status().value()))
    }

    @Test
    fun submitLesson_happyPath_returnsCorrectResponse() {

        val lessonId = l5.id!!
        val userId =
            setupUserCompletionForTest(
                3,
                course1.id!!,
                lessonId,
                l5.id!!,
                4,
                0,
                1,
                FIXED_TIMESTAMP_1,
                FIXED_TIMESTAMP_2
            )
        val nextLessonId = l6.id!!

        val response = submitLessonBody(userId, lessonId, course1.id!!)

        assertThat(response.userId).isEqualTo(userId)
        assertThat(response.lessonId).isEqualTo(lessonId)
        assertThat(response.newUserScore).isGreaterThan(15)
        assertThat(response.totalScore).isGreaterThan(15)
        assertThat(response.accuracy).isEqualTo(100)
        assertThat(response.message).isNotNull
        assertThat(response.updatedUserCourseProgress!!.currentLessonId).isEqualTo(nextLessonId)
    }

    @Test
    fun submitLesson_happyPath_returnsCorrectResponse_noUpdatedLessonId() {

        val lessonId = l4.id!!
        val userId =
            setupUserCompletionForTest(
                0,
                course1.id!!,
                lessonId,
                l5.id!!,
                4,
                0,
                1,
                FIXED_TIMESTAMP_1,
                FIXED_TIMESTAMP_2
            )
        val currentLessonId = l5.id!!

        val response = submitLessonBody(userId, lessonId, course1.id!!)

        assertThat(response.userId).isEqualTo(userId)
        assertThat(response.totalScore).isLessThanOrEqualTo(5)
        assertThat(response.accuracy).isEqualTo(0)
        assertThat(response.updatedUserCourseProgress!!.currentLessonId).isEqualTo(currentLessonId)
        assertThat(response.newUserScore).isLessThanOrEqualTo(5)
    }

    private fun submitLessonBody(
        userId: Int,
        lessonId: Int,
        courseId: Int
    ): LessonCompleteResponse {
        return given()
            .header("X-Test-User-Id", userId)
            .contentType("application/json")
            .body(
                mapOf(
                    "lessonId" to lessonId,
                    "sectionId" to courseId
                )
            )
            .`when`()
            .post(pathConstants.LESSONS_COMPLETIONS + pathConstants.SUBMIT_COMPLETED_LESSON)
            .then()
            .statusCode(200)
            .extract()
            .`as`(LessonCompleteResponse::class.java)
    }
}