package com.testingpractice.duoclonebackend.progress.app.service

import com.testingpractice.duoclonebackend.catalog.api.dto.LessonDto
import com.testingpractice.duoclonebackend.catalog.app.mapper.LessonMapper
import com.testingpractice.duoclonebackend.catalog.app.service.CurriculumNavigator
import com.testingpractice.duoclonebackend.catalog.app.service.LookupService
import com.testingpractice.duoclonebackend.catalog.domain.entity.Course
import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import com.testingpractice.duoclonebackend.catalog.infra.repository.CourseRepository
import com.testingpractice.duoclonebackend.commons.exception.ApiException
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import com.testingpractice.duoclonebackend.progress.infra.repository.LessonCompletionRepository
import com.testingpractice.duoclonebackend.progress.infra.repository.UserCourseProgressRepository
import com.testingpractice.duoclonebackend.user.domain.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.Instant

@Service
open class CourseProgressService(
    private val userCourseProgressRepository: UserCourseProgressRepository,
    private val curriculumNavigator: CurriculumNavigator,
    private val lookupService: LookupService,
    private val lessonCompletionRepository: LessonCompletionRepository,
    private val lessonMapper: LessonMapper,
    private val courseRepository: CourseRepository
) {

    @Transactional
    open fun updateUsersNextLesson(
        user: User,
        courseId: Int,
        currentLesson: Lesson,
        isCompleted: Boolean,
        scoreForLesson: Int
    ): List<LessonDto> {

        val userId = user.id!!

        if (user.currentCourseId != courseId)
            throw ApiException(ErrorCode.COURSE_MISMATCH)

        val userCourseProgress =
            userCourseProgressRepository.findByUserIdAndCourseId(userId, courseId)
                ?: throw ApiException(ErrorCode.USER_NOT_FOUND)

        val lessonsPassed = mutableListOf<LessonDto>()

        val currentProgressLessonId = userCourseProgress.currentLessonId
        val isCurrentCourseLesson =
            currentProgressLessonId == currentLesson.id

        var isCourseCompleted = userCourseProgress.isComplete ?: false

        if (!isCompleted) {
            val nextLessonDto =
                curriculumNavigator.getNextLesson(currentLesson, userId, courseId)

            if (nextLessonDto.isCourseCompleted) {
                isCourseCompleted = true
                userCourseProgress.isComplete = true
            }

            val nextLesson = nextLessonDto.nextLesson

            if (isCourseCompleted) {
                userCourseProgress.currentLessonId = currentLesson.id
            } else if (nextLesson != null) {
                userCourseProgress.currentLessonId = nextLesson.id
            } else {
                throw ApiException(ErrorCode.LESSON_NOT_FOUND)
            }
        }

        if (!isCompleted && !isCurrentCourseLesson && !isCourseCompleted) {
            val currentCourseProgressLesson =
                lookupService.lessonOrThrow(currentProgressLessonId!!)

            val skippedLessons =
                curriculumNavigator.getLessonsBetweenInclusive(
                    courseId,
                    currentCourseProgressLesson,
                    currentLesson,
                    userId
                )

            val now = Timestamp.from(Instant.now())

            for (skippedLesson in skippedLessons) {
                if (skippedLesson.id != currentLesson.id) {
                    lessonCompletionRepository.insertIfAbsent(
                        userId,
                        skippedLesson.id!!,
                        courseId,
                        0,
                        now
                    )
                    lessonsPassed.add(
                        lessonMapper.toDto(skippedLesson, true)
                    )
                }
            }
        }

        userCourseProgressRepository.save(userCourseProgress)

        return lessonsPassed
    }

    fun getUserCourses(userId: Int): List<Course> {
        val progresses = userCourseProgressRepository.findAllByUserId(userId)
        val courseIds = progresses.mapNotNull { it.courseId }
        return courseRepository.findAllById(courseIds)
    }

    fun getLessonSectionId(lessonId: Int): Int {
        val lesson = lookupService.lessonOrThrow(lessonId)
        val unit = lookupService.unitOrThrow(lesson.unitId!!)
        return unit.sectionId!!
    }
}