package com.testingpractice.duoclonebackend.testutils;

import com.testingpractice.duoclonebackend.entity.*;
import com.testingpractice.duoclonebackend.follow.domain.entity.Follow;

import java.sql.Timestamp;
import java.time.LocalDate;

import static com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_1;

public class TestUtils {

  public static Unit makeUnit(String title, int courseId, int section, int orderIndex) {
    return Unit.builder()
        .title(title)
        .description("Default description")
        .orderIndex(orderIndex)
        .courseId(courseId)
        .sectionId(section)
            .color("GREEN")
            .animationPath("Default animation path")
        .build();
  }

  public static QuestDefinition makeQuestDefition (String code, Integer target, Integer rewardPoints, boolean active) {
    return QuestDefinition.builder().code(code).target(target).rewardPoints(rewardPoints).active(active).build();
  }

  public static MonthlyChallengeDefinition makeMonthlyChallengeDefinition (String code, Integer target, Integer rewardPoints, boolean active) {
    return MonthlyChallengeDefinition.builder().code(code).target(target).rewardPoints(rewardPoints).active(active).build();
  }

  public static User makeUser(Integer currentCourseId, String username, String firstName, String lastName, String email, String pfpSrc, Integer points, Timestamp createdAt, Timestamp lastSubmission, Integer streakLength) {
    return User.builder().currentCourseId(currentCourseId).username(username).firstName(firstName).lastName(lastName).email(email).pfpSrc(pfpSrc).points(points).createdAt(createdAt).lastSubmission(lastSubmission).streakLength(streakLength).build();
  }

  public static Follow makeFollow(
          Integer followerId,
          Integer followedId,
          Timestamp createdAt
  ) {
    return new Follow(
            null,
            followerId,
            followedId,
            createdAt
    );
  }

  public static UserMonthlyChallenge makeUserMonthlyChallenge (Integer userId, Integer defId, LocalDate today, Integer progress) {

    return UserMonthlyChallenge.builder()
            .id(UserMonthlyChallengeId.builder()
                    .userId(userId)
                    .challengeDefId(defId)
                    .year(today.getYear())
                    .month(today.getMonthValue())
                    .build())
            .progress(progress)
            .rewardClaimed(false)
            .completedAt(null)
            .build();
  }

  public static UserDailyQuest makeUserDailyQuest (Integer userId, Integer defId, LocalDate today, Integer progress) {

    return UserDailyQuest.builder()
            .id(UserDailyQuestId.builder()
                    .userId(userId)
                    .questDefId(defId)
                    .date(today)
                    .build())
            .progress(progress)
            .rewardClaimed(false)
            .completedAt(null)
            .build();
  }

  public static Course makeCourse(String title, String imgSrc) {
    return Course.builder().title(title).imgSrc(imgSrc).build();
  }

  public static UserCourseProgress makeUserCourseProgress(Integer userId, Integer courseId, Boolean isComplete, Integer currentLessonId, Timestamp updatedAt) {
    return UserCourseProgress.builder().userId(userId).courseId(courseId).isComplete(isComplete).currentLessonId(currentLessonId).updatedAt(updatedAt) .build();
  }

  public static Section makeSection(String title, Integer courseId, Integer orderIndex) {
    return Section.builder().title(title).courseId(courseId).orderIndex(orderIndex).build();
  }

  public static Exercise makeExercise(Integer lessonId, String prompt, Integer orderIndex) {
    return Exercise.builder().lessonId(lessonId).prompt(prompt).orderIndex(orderIndex).type("CLOZE").build();
  }

  public static ExerciseAttempt makeExerciseAttempt(
          Integer exerciseId, Integer userId, boolean isChecked, Timestamp submittedAt, Integer optionId, Integer score
  ) {
    return ExerciseAttempt.builder()
            .exerciseId(exerciseId)
            .userId(userId)
            .isChecked(isChecked)
            .submittedAt(submittedAt)
            .optionId(optionId)
            .score(score)
            .build();
  }

  public static Course makeCourse(String title) {
    return Course.builder().title(title).imgSrc("Default").build();
  }

  public static Lesson makeLesson(String title, Integer unitId, Integer orderIndex, String lessonType) {
    return Lesson.builder().title(title).unitId(unitId).orderIndex(orderIndex).lessonType(lessonType).build();
  }

  public static LessonCompletion makeLessonCompletion(
          Integer userId, Integer lessonId, Integer courseId, Integer score
  ) {
    return LessonCompletion.builder()
            .id(new LessonCompletionId(userId, lessonId))
            .score(100)
            .courseId(1)
            .completedAt(new Timestamp(System.currentTimeMillis()))
            .build();
  }

}
