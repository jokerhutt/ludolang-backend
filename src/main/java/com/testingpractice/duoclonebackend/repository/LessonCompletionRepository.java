package com.testingpractice.duoclonebackend.repository;

import com.testingpractice.duoclonebackend.entity.LessonCompletion;
import com.testingpractice.duoclonebackend.entity.LessonCompletionId;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonCompletionRepository
    extends JpaRepository<LessonCompletion, LessonCompletionId> {

  boolean existsByIdUserIdAndIdLessonId(Integer userId, Integer lessonId);

  @Query(
      """
    select lc.id.lessonId
    from LessonCompletion lc
    where lc.id.userId = :userId and lc.id.lessonId in :lessonIds
  """)
  List<Integer> findCompletedLessonIdsIn(
      @Param("userId") Integer userId, @Param("lessonIds") Collection<Integer> lessonIds);

  @Modifying
  @Query(
      value =
          """
  INSERT IGNORE INTO lesson_completions
    (user_id, lesson_id, course_id, score, completed_at)
  VALUES (:userId, :lessonId, :courseId, :score, :completedAt)
  """,
      nativeQuery = true)
  int insertIfAbsent(long userId, long lessonId, long courseId, int score, Timestamp completedAt);

  @Query(
"""
  select count(lc)
  from LessonCompletion lc
  where lc.id.userId = :userId and lc.courseId = :courseId
""")
  Integer countByUserAndCourse(
      @Param("userId") Integer userId, @Param("courseId") Integer courseId);
}
