package com.testingpractice.duoclonebackend.catalog.app.service

import com.testingpractice.duoclonebackend.catalog.api.dto.ExerciseDto
import com.testingpractice.duoclonebackend.catalog.infra.repository.ExerciseRepository
import com.testingpractice.duoclonebackend.progress.infra.repository.ExerciseAttemptRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Comparator

@Service
open class ExerciseService(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseAttemptRepository: ExerciseAttemptRepository,
    private val exerciseOptionService: ExerciseOptionService
) {

    @Transactional
    open fun getExercisesForLesson(lessonId: Int, userId: Int): List<ExerciseDto> {

        exerciseAttemptRepository.markUncheckedByUserAndLesson(userId, lessonId)

        val exercises = ArrayList(exerciseRepository.findAllByLessonId(lessonId))
        exercises.sortWith(Comparator.comparingInt { it.orderIndex!! })

        return exerciseOptionService.getRandomizedExercisesForLesson(exercises, userId)
    }
}