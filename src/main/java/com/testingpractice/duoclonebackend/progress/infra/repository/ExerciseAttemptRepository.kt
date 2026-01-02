package com.testingpractice.duoclonebackend.progress.infra.repository

import com.testingpractice.duoclonebackend.progress.domain.entity.ExerciseAttempt
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface ExerciseAttemptRepository :
    JpaRepository<ExerciseAttempt, Int> {

    @Query(
        """
        SELECT exerciseAttempt FROM ExerciseAttempt exerciseAttempt
        WHERE exerciseAttempt.userId = :userId
          AND exerciseAttempt.exerciseId IN :exerciseIds
          AND exerciseAttempt.isChecked = false
        """
    )
    fun findAllByExerciseIdInAndUserIdAndUnchecked(
        @Param("exerciseIds") exerciseIds: Collection<Int>,
        @Param("userId") userId: Int
    ): List<ExerciseAttempt>

    @Modifying
    @Query(
        """
        UPDATE ExerciseAttempt exerciseAttempt
           SET exerciseAttempt.isChecked = true
         WHERE exerciseAttempt.userId = :userId
           AND exerciseAttempt.isChecked = false
           AND exerciseAttempt.exerciseId IN (
                SELECT e.id FROM Exercise e WHERE e.lessonId = :lessonId
           )
        """
    )
    fun markUncheckedByUserAndLesson(
        @Param("userId") userId: Int,
        @Param("lessonId") lessonId: Int
    ): Int
}