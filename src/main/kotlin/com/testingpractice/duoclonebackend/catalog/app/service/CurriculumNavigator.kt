package com.testingpractice.duoclonebackend.catalog.app.service

import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import com.testingpractice.duoclonebackend.catalog.infra.repository.LessonRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.SectionRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.UnitRepository
import com.testingpractice.duoclonebackend.commons.exception.ApiException
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import com.testingpractice.duoclonebackend.progress.api.dto.NextLessonDto
import org.springframework.stereotype.Service

@Service
class CurriculumNavigator(
    private val lessonRepository: LessonRepository,
    private val unitRepository: UnitRepository,
    private val sectionRepository: SectionRepository
) {

    fun getNextLesson(
        lesson: Lesson,
        userId: Int,
        courseId: Int
    ): NextLessonDto {

        val nextLessonInUnit =
            lessonRepository.findFirstByUnitIdAndOrderIndexGreaterThanOrderByOrderIndexAsc(
                lesson.unitId!!,
                lesson.orderIndex!!
            )

        if (nextLessonInUnit != null) {
            return NextLessonDto(nextLessonInUnit, false)
        }

        val currentUnit =
            unitRepository.findById(lesson.unitId!!).orElseThrow {
                ApiException(ErrorCode.UNIT_NOT_FOUND)
            }

        val nextUnit =
            unitRepository.findFirstBySectionIdAndOrderIndexGreaterThanOrderByOrderIndexAsc(
                currentUnit.sectionId!!,
                currentUnit.orderIndex!!
            )

        return if (nextUnit != null) {
            val nextLesson =
                lessonRepository.findFirstByUnitIdOrderByOrderIndexAsc(nextUnit.id!!)
                    ?: throw ApiException(ErrorCode.LESSON_NOT_FOUND)

            NextLessonDto(nextLesson, false)
        } else {
            NextLessonDto(null, true)
        }
    }

    fun getLessonsBetweenInclusive(
        courseId: Int,
        from: Lesson,
        to: Lesson,
        userId: Int
    ): List<Lesson> {

        val out = mutableListOf<Lesson>()
        var cur = from
        out.add(cur)

        var guard = 0
        while (cur.id != to.id) {
            cur = getNextLesson(cur, userId, courseId).nextLesson
                ?: throw ApiException(ErrorCode.LESSON_NOT_FOUND)

            out.add(cur)

            if (++guard > 10_000) {
                throw IllegalStateException("Guard tripped while traversing lessons")
            }
        }

        return out
    }
}