package com.testingpractice.duoclonebackend.catalog.app.service

import com.testingpractice.duoclonebackend.catalog.api.dto.ExerciseDto
import com.testingpractice.duoclonebackend.catalog.app.mapper.ExerciseMapper
import com.testingpractice.duoclonebackend.catalog.domain.entity.Exercise
import com.testingpractice.duoclonebackend.catalog.domain.entity.ExerciseOption
import com.testingpractice.duoclonebackend.catalog.infra.repository.ExerciseOptionRepository
import com.testingpractice.duoclonebackend.commons.exception.ApiException
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class ExerciseOptionService(
    private val exerciseOptionRepository: ExerciseOptionRepository,
    private val exerciseMapper: ExerciseMapper
) {

    fun getRandomizedExercisesForLesson(
        exercises: List<Exercise>,
        userId: Int
    ): List<ExerciseDto> {

        if (exercises.isEmpty()) {
            throw ApiException(ErrorCode.EXERCISES_NOT_FOUND)
        }

        val lessonId = exercises[0].lessonId
        val seed = Objects.hash(lessonId, userId, LocalDate.now())
        val rnd = Random(seed.toLong())

        return exercises.map { ex ->
            val opts = ArrayList(
                exerciseOptionRepository.findAllByExerciseId(ex.id!!)
            )
            Collections.shuffle(opts, rnd)
            exerciseMapper.toDto(ex, opts)
        }
    }

    fun getCorrectExerciseResponses(
        optionIds: ArrayList<Int>,
        correctOptions: List<Int>
    ): ArrayList<Int> {

        val correctResponses = ArrayList<Int>()

        for (i in optionIds.indices) {
            if (
                i < correctOptions.size &&
                correctOptions[i] == optionIds[i]
            ) {
                correctResponses.add(optionIds[i])
            }
        }

        return correctResponses
    }

    fun getCorrectExerciseAnswerText(correctOptions: List<Int>): String {
        val correctExerciseOptions =
            exerciseOptionRepository.findAllByIdIn(correctOptions)

        return parseCorrectAnswer(correctExerciseOptions)
    }

    fun areOptionsCorrect(
        optionIds: ArrayList<Int>,
        correctOptions: List<Int>
    ): Boolean {
        return correctOptions == optionIds
    }

    private fun parseCorrectAnswer(correctOptions: List<ExerciseOption>): String {
        return correctOptions
            .sortedBy { it.answerOrder }
            .joinToString(" ") { it.content ?: "" }
    }
}