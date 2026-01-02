package com.testingpractice.duoclonebackend.progress.app.service

import com.testingpractice.duoclonebackend.catalog.app.mapper.LessonMapper
import com.testingpractice.duoclonebackend.catalog.app.service.LookupService
import com.testingpractice.duoclonebackend.progress.api.dto.LessonCompleteResponse
import com.testingpractice.duoclonebackend.progress.api.dto.NewStreakCount
import com.testingpractice.duoclonebackend.progress.app.util.AccuracyScoreUtils
import com.testingpractice.duoclonebackend.progress.infra.repository.LessonCompletionRepository
import com.testingpractice.duoclonebackend.quest.app.service.QuestService
import com.testingpractice.duoclonebackend.quest.domain.enums.QuestCode
import com.testingpractice.duoclonebackend.user.app.service.UserService
import com.testingpractice.duoclonebackend.user.infra.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.Instant

@Service
open class LessonCompletionService(
    private val lookupService: LookupService,
    private val streakService: StreakService,
    private val exerciseAttemptService: ExerciseAttemptService,
    private val lessonCompletionRepository: LessonCompletionRepository,
    private val courseProgressService: CourseProgressService,
    private val userRepository: UserRepository,
    private val lessonMapper: LessonMapper,
    private val questService: QuestService,
    private val userService: UserService
) {

    @Transactional
    open fun getCompletedLesson(
        lessonId: Int,
        userId: Int,
        courseId: Int
    ): LessonCompleteResponse {

        val exerciseAttempts =
            exerciseAttemptService.getLessonExerciseAttemptsForUser(
                lessonId,
                userId
            )

        val user = lookupService.userOrThrow(userId)
        val lesson = lookupService.lessonOrThrow(lessonId)

        val isCompleted = isLessonComplete(userId, lessonId)

        val lessonAccuracy =
            AccuracyScoreUtils.getLessonAccuracy(exerciseAttempts)

        val scoreForLesson =
            AccuracyScoreUtils.getLessonPoints(
                exerciseAttempts,
                !isCompleted,
                lessonAccuracy
            )

        // -- UPDATE USER SCORE --
        user.points = (user.points ?: 0) + scoreForLesson

        // -- MARK ATTEMPTS AS CHECKED --
        exerciseAttemptService.markAttemptsAsChecked(userId, lessonId)

        // -- UPDATE USER STREAK --
        val newStreakCount =
            streakService.updateUserStreak(user)

        // -- UPDATE USERS NEXT LESSON --
        val passedLessons =
            courseProgressService.updateUsersNextLesson(
                user,
                courseId,
                lesson,
                isCompleted,
                scoreForLesson
            )

        val userCourseProgressDto =
            userService.getUserCourseProgress(courseId, userId)

        lessonCompletionRepository.insertIfAbsent(
            userId,
            lessonId,
            courseId,
            scoreForLesson,
            Timestamp.from(Instant.now())
        )

        userRepository.save(user)

        updateQuests(
            userId,
            lessonAccuracy,
            newStreakCount
        )

        return LessonCompleteResponse(
            userId,
            scoreForLesson,
            user.points ?: 0,
            lessonAccuracy,
            lessonId,
            lessonMapper.toDto(
                lesson,
                lessonCompletionRepository
                    .existsByIdUserIdAndIdLessonId(userId, lessonId)
            ),
            passedLessons,
            userCourseProgressDto,
            newStreakCount,
            AccuracyScoreUtils.getAccuracyMessage(lessonAccuracy)
        )
    }

    private fun isLessonComplete(
        userId: Int,
        lessonId: Int
    ): Boolean {
        return lessonCompletionRepository
            .existsByIdUserIdAndIdLessonId(userId, lessonId)
    }

    @Transactional
    protected open fun updateQuests(
        userId: Int,
        lessonAccuracy: Int,
        newStreakCount: NewStreakCount
    ) {

        if (lessonAccuracy == 100) {
            questService.updateQuestProgress(
                userId,
                QuestCode.PERFECT
            )
        }

        if (lessonAccuracy > 90) {
            questService.updateQuestProgress(
                userId,
                QuestCode.ACCURACY
            )
        }

        val prev = newStreakCount.oldCount ?: 0
        val next = newStreakCount.newCount ?: 0

        if (next > prev) {
            questService.updateQuestProgress(
                userId,
                QuestCode.STREAK
            )
        }
    }
}