package com.testingpractice.duoclonebackend.catalog.app.mapper

import com.testingpractice.duoclonebackend.catalog.api.dto.ExerciseDto
import com.testingpractice.duoclonebackend.catalog.domain.entity.Exercise
import com.testingpractice.duoclonebackend.catalog.domain.entity.ExerciseOption
import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class ExerciseMapper(
    private val basicMapper: BasicMapper,
    private val exerciseOptionMapper: ExerciseOptionMapper
) {

    fun toDto(
        exercise: Exercise,
        options: List<ExerciseOption>
    ): ExerciseDto =
        basicMapper.one(exercise) {
            ExerciseDto(
                it.id,
                it.lessonId,
                it.prompt,
                it.type,
                it.orderIndex,
                exerciseOptionMapper.toDtoList(options)
            )
        }
}