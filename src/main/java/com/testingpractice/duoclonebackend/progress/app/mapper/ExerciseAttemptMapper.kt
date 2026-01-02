package com.testingpractice.duoclonebackend.progress.app.mapper
import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import com.testingpractice.duoclonebackend.progress.api.dto.ExerciseAttemptDto
import com.testingpractice.duoclonebackend.progress.domain.entity.ExerciseAttempt
import org.springframework.stereotype.Component

@Component
class ExerciseAttemptMapper(
    private val basicMapper: BasicMapper
) {

    fun toDto(attempt: ExerciseAttempt): ExerciseAttemptDto =
        basicMapper.one(attempt) {
            ExerciseAttemptDto(
                it.id,
                it.exerciseId,
                it.userId,
                it.isChecked,
                it.submittedAt,
                it.optionId,
                it.score
            )
        }

    fun toDtoList(attempts: List<ExerciseAttempt>): List<ExerciseAttemptDto> =
        basicMapper.list(attempts) { attempt ->
            toDto(attempt)
        }
}