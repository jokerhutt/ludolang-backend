package com.testingpractice.duoclonebackend.catalog.app.mapper

import com.testingpractice.duoclonebackend.catalog.api.dto.ExerciseOptionDto
import com.testingpractice.duoclonebackend.catalog.domain.entity.ExerciseOption
import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class ExerciseOptionMapper(
    private val basicMapper: BasicMapper
) {

    fun toDto(option: ExerciseOption): ExerciseOptionDto =
        basicMapper.one(option) {
            ExerciseOptionDto(
                it.id,
                it.exerciseId,
                it.content,
                it.imageUrl,
                it.isCorrect
            )
        }

    fun toDtoList(options: List<ExerciseOption>): List<ExerciseOptionDto> =
        basicMapper.list(options) { option ->
            toDto(option)
        }
}