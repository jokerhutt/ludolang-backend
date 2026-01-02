package com.testingpractice.duoclonebackend.catalog.app.service

import com.testingpractice.duoclonebackend.catalog.domain.entity.Course
import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import com.testingpractice.duoclonebackend.catalog.domain.entity.LudoUnit
import com.testingpractice.duoclonebackend.catalog.infra.repository.CourseRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.LessonRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.UnitRepository
import com.testingpractice.duoclonebackend.commons.exception.ApiException
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import com.testingpractice.duoclonebackend.progress.infra.repository.UserCourseProgressRepository
import com.testingpractice.duoclonebackend.user.domain.entity.User
import com.testingpractice.duoclonebackend.user.infra.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class LookupService(
    private val userRepository: UserRepository,
    private val lessonRepository: LessonRepository,
    private val unitRepository: UnitRepository,
    private val courseRepository: CourseRepository,
    private val userCourseProgressRepository: UserCourseProgressRepository
) {

    fun userOrThrow(userId: Int): User {
        return userRepository
            .findById(userId)
            .orElseThrow { ApiException(ErrorCode.USER_NOT_FOUND) }
    }

    fun lessonOrThrow(lessonId: Int): Lesson {
        return lessonRepository
            .findById(lessonId)
            .orElseThrow { ApiException(ErrorCode.LESSON_NOT_FOUND) }
    }

    fun unitOrThrow(unitId: Int): LudoUnit {
        return unitRepository
            .findById(unitId)
            .orElseThrow { ApiException(ErrorCode.UNIT_NOT_FOUND) }
    }

    fun courseOrThrow(courseId: Int): Course {
        return courseRepository
            .findById(courseId)
            .orElseThrow { ApiException(ErrorCode.COURSE_NOT_FOUND) }
    }
}