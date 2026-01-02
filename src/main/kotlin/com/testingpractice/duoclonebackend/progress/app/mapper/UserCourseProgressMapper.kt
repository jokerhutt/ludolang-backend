package com.testingpractice.duoclonebackend.progress.app.mapper

import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import com.testingpractice.duoclonebackend.progress.api.dto.UserCourseProgressDto
import com.testingpractice.duoclonebackend.progress.domain.entity.UserCourseProgress
import org.springframework.stereotype.Component

@Component
class UserCourseProgressMapper(
    private val basicMapper: BasicMapper
) {

    fun toDto(
        entity: UserCourseProgress,
        completedLessons: Int?,
        sectionId: Int?
    ): UserCourseProgressDto =
        basicMapper.one(entity) {
            UserCourseProgressDto(
                it.id,
                it.userId,
                it.courseId,
                sectionId,
                it.isComplete,
                it.currentLessonId,
                completedLessons
            )
        }
}