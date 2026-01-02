package com.testingpractice.duoclonebackend.catalog.app.service

import com.testingpractice.duoclonebackend.catalog.domain.entity.Course
import com.testingpractice.duoclonebackend.catalog.infra.repository.CourseRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.LessonRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.SectionRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.UnitRepository
import com.testingpractice.duoclonebackend.commons.exception.ApiException
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import com.testingpractice.duoclonebackend.progress.api.dto.ChangeCourseDto
import com.testingpractice.duoclonebackend.progress.app.service.CourseProgressService
import com.testingpractice.duoclonebackend.progress.domain.entity.UserCourseProgress
import com.testingpractice.duoclonebackend.progress.infra.repository.UserCourseProgressRepository
import com.testingpractice.duoclonebackend.user.app.mapper.UserMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.Instant

@Service
open class CourseService(
    private val courseRepository: CourseRepository,
    private val lookupService: LookupService,
    private val userMapper: UserMapper,
    private val sectionRepository: SectionRepository,
    private val unitRepository: UnitRepository,
    private val lessonRepository: LessonRepository,
    private val userCourseProgressRepository: UserCourseProgressRepository,
    private val courseProgressService: CourseProgressService
) {

    fun getAllCourses(): List<Course> {
        return courseRepository.findAll()
    }

    @Transactional
    open fun changeUserCourse(userId: Int, newCourseId: Int): ChangeCourseDto {
        val user = lookupService.userOrThrow(userId)

        val existingProgress =
            userCourseProgressRepository.findByUserIdAndCourseId(userId, newCourseId)

        if (existingProgress == null) {
            val newProgress = UserCourseProgress()
            newProgress.userId = userId
            newProgress.courseId = newCourseId
            newProgress.isComplete = false
            newProgress.currentLessonId = getFirstLessonIdOfCourse(newCourseId)
            newProgress.updatedAt = Timestamp.from(Instant.now())
            userCourseProgressRepository.save(newProgress)
        }

        user.currentCourseId = newCourseId

        val updatedUserCourses = courseProgressService.getUserCourses(userId)

        return ChangeCourseDto(
            userMapper.toUserResponse(user),
            updatedUserCourses
        )
    }

    fun getFirstLessonIdOfCourse(courseId: Int): Int {
        val course = lookupService.courseOrThrow(courseId)

        val section =
            sectionRepository.findFirstByCourseIdOrderByOrderIndexAsc(course.id!!)
                ?: throw ApiException(ErrorCode.SECTION_NOT_FOUND)

        val firstUnit =
            unitRepository.findFirstBySectionIdOrderByOrderIndexAsc(section.id!!)
                ?: throw ApiException(ErrorCode.SECTION_NOT_FOUND)

        val firstLesson =
            lessonRepository.findFirstByUnitIdOrderByOrderIndexAsc(firstUnit.id!!)
                ?: throw ApiException(ErrorCode.UNIT_NOT_FOUND)

        return firstLesson.id!!
    }
}