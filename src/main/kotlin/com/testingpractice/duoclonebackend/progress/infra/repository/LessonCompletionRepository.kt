package com.testingpractice.duoclonebackend.progress.infra.repository

import com.testingpractice.duoclonebackend.progress.domain.entity.LessonCompletion
import com.testingpractice.duoclonebackend.progress.domain.entity.embeddable.LessonCompletionId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Timestamp
import java.util.Collection

interface LessonCompletionRepository :
    JpaRepository<LessonCompletion, LessonCompletionId> {

    fun existsByIdUserIdAndIdLessonId(
        userId: Int,
        lessonId: Int
    ): Boolean

    @Query(
        """
        select lc.id.lessonId
        from LessonCompletion lc
        where lc.id.userId = :userId
          and lc.id.lessonId in :lessonIds
        """
    )
    fun findCompletedLessonIdsIn(
        @Param("userId") userId: Int,
        @Param("lessonIds") lessonIds: Collection<Int>
    ): List<Int>

    @Modifying
    @Query(
        value = """
            INSERT IGNORE INTO lesson_completions
              (user_id, lesson_id, course_id, score, completed_at)
            VALUES (:userId, :lessonId, :courseId, :score, :completedAt)
        """,
        nativeQuery = true
    )
    fun insertIfAbsent(
        @Param("userId") userId: Int,
        @Param("lessonId") lessonId: Int,
        @Param("courseId") courseId: Int,
        @Param("score") score: Int,
        @Param("completedAt") completedAt: Timestamp
    ): Int

    @Query(
        """
        select count(lc)
        from LessonCompletion lc
        where lc.id.userId = :userId
          and lc.courseId = :courseId
        """
    )
    fun countByUserAndCourse(
        @Param("userId") userId: Int,
        @Param("courseId") courseId: Int
    ): Int
}