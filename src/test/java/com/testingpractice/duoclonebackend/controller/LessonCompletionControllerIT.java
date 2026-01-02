package com.testingpractice.duoclonebackend.controller;

import static com.testingpractice.duoclonebackend.testutils.TestConstants.*;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.testingpractice.duoclonebackend.commons.constants.pathConstants;
import com.testingpractice.duoclonebackend.dto.LessonCompleteResponse;
import com.testingpractice.duoclonebackend.entity.*;

import java.util.Map;

import com.testingpractice.duoclonebackend.commons.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LessonCompletionControllerIT extends AbstractIntegrationTest {

  @BeforeEach
  void seed() {

  }

  @Test
  void submitLesson_happyPath_endCourse_returnsCourseComplete () {

    Integer lessonId = l6.getId();
    Integer userId = setupUserCompletionForTest(3, course1.getId(), lessonId, l6.getId(), 5, 0, 1, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2);


    LessonCompleteResponse response = submitLessonBody(userId, lessonId, course1.getId());

    assertThat(response.updatedUserCourseProgress().isComplete()).isTrue();
    assertThat(response.message()).isNotNull();
    assertThat(response.updatedUserCourseProgress().currentLessonId().equals(lessonId));

  }

  @Test
  void submitLesson_happyPath_jumpAhead_returnsListToUpdate () {

    Integer lessonId = l5.getId();
    Integer userId = setupUserCompletionForTest(3, course1.getId(), lessonId, l3.getId(), 2, 0, 1, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2);
    Integer nextLessonId = l6.getId();

    LessonCompleteResponse response = submitLessonBody(userId, lessonId, course1.getId());

    assertThat(response.updatedUserCourseProgress().currentLessonId()).isEqualTo(nextLessonId);
    assertThat(response.lessonsToUpdate().size()).isEqualTo(2);

  }

  @Test
  void submitLesson_courseMismatch_throwsException () {

    Course course2 = courseRepository.save(makeCourse("Course 2", "Title 2"));
    Section section2 = sectionRepository.save(makeSection("Section 2", course2.getId(), 1));
    Unit unit2 = unitRepository.save(makeUnit("Unit 2", course2.getId(), section2.getId(), 1));
    Lesson l1_2 = lessonRepository.save(makeLesson("Lesson 1.2", unit2.getId(), 1,  "CLOZE"));

    Integer lessonId = l5.getId();
    Integer userId = setupUserCompletionForTest(3, course2.getId(), lessonId, l1_2.getId(), 4, 40, 1, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2);
    UserCourseProgress userCourseProgress = userCourseProgressRepository.save(makeUserCourseProgress(userId, course1.getId(),  false, l5.getId(), FIXED_TIMESTAMP_2));
    given()
            .header("X-Test-User-Id", userId)
            .contentType("application/json")
            .body(
                    Map.of(
                            "lessonId", lessonId,
                            "sectionId", course1.getId()))
            .when()
            .post(pathConstants.LESSONS_COMPLETIONS + pathConstants.SUBMIT_COMPLETED_LESSON)
            .then()
            .statusCode(ErrorCode.COURSE_MISMATCH.status().value())
            .body("title", equalTo(ErrorCode.COURSE_MISMATCH.name()))
            .body("detail", equalTo(ErrorCode.COURSE_MISMATCH.defaultMessage()))
            .body("status", equalTo(ErrorCode.COURSE_MISMATCH.status().value()));



  }


  @Test
  void submitLesson_happyPath_returnsCorrectResponse () {

    Integer lessonId = l5.getId();
    Integer userId = setupUserCompletionForTest(3, course1.getId(), lessonId, l5.getId(), 4, 0, 1, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2);
    Integer nextLessonId = l6.getId();

    LessonCompleteResponse response = submitLessonBody(userId, lessonId, course1.getId());

    assertThat(response.userId()).isEqualTo(userId);
    assertThat(response.lessonId()).isEqualTo(lessonId);
    assertThat(response.newUserScore()).isGreaterThan(15);
    assertThat(response.totalScore()).isGreaterThan(15);
    assertThat(response.accuracy()).isEqualTo(100);
    assertThat(response.message()).isNotNull();
    assertThat(response.updatedUserCourseProgress().currentLessonId()).isEqualTo(nextLessonId);

  }


  @Test
  void submitLesson_happyPath_returnsCorrectResponse_noUpdatedLessonId () {

    Integer lessonId = l4.getId();
    Integer userId = setupUserCompletionForTest(0, course1.getId(), lessonId, l5.getId(), 4, 0, 1, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2);
    Integer currentLessonId = l5.getId();

    LessonCompleteResponse response = submitLessonBody(userId, lessonId, course1.getId());
    assertThat(response.userId()).isEqualTo(userId);
    assertThat(response.totalScore()).isLessThanOrEqualTo(5);
    assertThat(response.accuracy()).isEqualTo(0);
    assertThat(response.updatedUserCourseProgress().currentLessonId()).isEqualTo(currentLessonId);
    assertThat(response.newUserScore()).isLessThanOrEqualTo(5);

  }

  private LessonCompleteResponse submitLessonBody (Integer userId, Integer lessonId, Integer courseId) {
    return
        given()
            .header("X-Test-User-Id", userId)
            .contentType("application/json")
            .body(
                Map.of(
                    "lessonId", lessonId,
                    "sectionId", courseId))
            .when()
            .post(pathConstants.LESSONS_COMPLETIONS + pathConstants.SUBMIT_COMPLETED_LESSON)
            .then()
            .statusCode(200)
            .extract()
            .as(LessonCompleteResponse.class);
  }





}
