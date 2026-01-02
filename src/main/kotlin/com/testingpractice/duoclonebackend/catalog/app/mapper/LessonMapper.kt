package com.testingpractice.duoclonebackend.catalog.app.mapper

import com.testingpractice.duoclonebackend.catalog.api.dto.LessonDto
import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class LessonMapper(
    private val basicMapper: BasicMapper
) {

    fun toDto(lesson: Lesson, passed: Boolean): LessonDto =
        basicMapper.one(lesson) {
            LessonDto(
                it.id,
                it.title,
                it.unitId,
                it.orderIndex,
                it.lessonType,
                passed
            )
        }

    fun toDtoList(
        lessons: List<Lesson>,
        completedIds: Set<Int>
    ): List<LessonDto> =
        basicMapper.list(lessons) { lesson ->
            toDto(lesson, completedIds.contains(lesson.id))
        }
}