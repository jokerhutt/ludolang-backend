package com.testingpractice.duoclonebackend.progress.app.service

import com.testingpractice.duoclonebackend.catalog.app.service.ExerciseOptionService
import com.testingpractice.duoclonebackend.catalog.infra.repository.ExerciseOptionRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.ExerciseRepository
import com.testingpractice.duoclonebackend.progress.api.dto.ExerciseAttemptResponse
import com.testingpractice.duoclonebackend.progress.domain.entity.AttemptOption
import com.testingpractice.duoclonebackend.progress.domain.entity.ExerciseAttempt
import com.testingpractice.duoclonebackend.progress.infra.repository.ExerciseAttemptOptionRepository
import com.testingpractice.duoclonebackend.progress.infra.repository.ExerciseAttemptRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.stream.IntStream

@Service
open class ExerciseAttemptService(
    private val exerciseAttemptRepository: ExerciseAttemptRepository,
    private val exerciseRepository: ExerciseRepository,
    private val exerciseOptionRepository: ExerciseOptionRepository,
    private val exerciseOptionService: ExerciseOptionService,
    private val exerciseAttemptOptionRepository: ExerciseAttemptOptionRepository
) {

    fun getLessonExerciseAttemptsForUser(
        lessonId: Int,
        userId: Int
    ): List<ExerciseAttempt> {

        val lessonExercises = exerciseRepository.findAllByLessonId(lessonId)
        val exerciseIds = lessonExercises.mapNotNull { it.id }

        return exerciseAttemptRepository
            .findAllByExerciseIdInAndUserIdAndUnchecked(exerciseIds, userId)
    }

    @Transactional
    open fun markAttemptsAsChecked(userId: Int, lessonId: Int) {
        exerciseAttemptRepository.markUncheckedByUserAndLesson(userId, lessonId)
    }

    @Transactional
    open fun submitExerciseAttempt(
        exerciseId: Int,
        optionIds: ArrayList<Int>,
        userId: Int
    ): ExerciseAttemptResponse {

        val options = exerciseOptionRepository.findAllByIdIn(optionIds)
        val correctOptions =
            exerciseOptionRepository.findCorrectOptionIds(exerciseId)

        val areCorrect =
            exerciseOptionService.areOptionsCorrect(optionIds, correctOptions)

        val attempt = ExerciseAttempt()
        attempt.optionId = options.first().id
        attempt.exerciseId = exerciseId
        attempt.userId = userId
        attempt.submittedAt = Timestamp(System.currentTimeMillis())

        updateScore(attempt, areCorrect)
        exerciseAttemptRepository.save(attempt)

        val attemptOptions = getAttemptOptions(attempt, optionIds)
        exerciseAttemptOptionRepository.saveAll(attemptOptions)

        return getExerciseAttemptResponseMessage(
            optionIds,
            exerciseId,
            attempt,
            areCorrect
        )
    }

    private fun updateScore(attempt: ExerciseAttempt, areCorrect: Boolean) {
        attempt.score = if (areCorrect) 5 else 0
    }

    private fun getExerciseAttemptResponseMessage(
        optionIds: ArrayList<Int>,
        exerciseId: Int,
        attempt: ExerciseAttempt,
        areCorrect: Boolean
    ): ExerciseAttemptResponse {

        val correctOptions =
            exerciseOptionRepository.findCorrectOptionIds(exerciseId)

        val correctResponses =
            exerciseOptionService.getCorrectExerciseResponses(
                optionIds,
                correctOptions
            )

        val correctAnswer =
            exerciseOptionService.getCorrectExerciseAnswerText(correctOptions)

        return if (areCorrect) {
            ExerciseAttemptResponse(
                true,
                attempt.score ?: 0,
                "Correct!",
                correctResponses,
                correctAnswer
            )
        } else {
            ExerciseAttemptResponse(
                false,
                attempt.score ?: 0,
                "Incorrect!",
                correctResponses,
                correctAnswer
            )
        }
    }

    private fun getAttemptOptions(
        attempt: ExerciseAttempt,
        optionIds: ArrayList<Int>
    ): List<AttemptOption> {

        return IntStream.range(0, optionIds.size)
            .mapToObj { i ->
                val attemptOption = AttemptOption()
                attemptOption.attemptId = attempt.id
                attemptOption.optionId = optionIds[i]
                attemptOption.position = i + 1
                attemptOption
            }
            .toList()
    }
}