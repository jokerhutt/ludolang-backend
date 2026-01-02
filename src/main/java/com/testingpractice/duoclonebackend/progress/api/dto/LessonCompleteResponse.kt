package com.testingpractice.duoclonebackend.progress.api.dto

import com.testingpractice.duoclonebackend.catalog.api.dto.LessonDto

data class LessonCompleteResponse(
    val userId: Int?,
    val totalScore: Int?,
    val newUserScore: Int?,
    val accuracy: Int?,
    val lessonId: Int?,
    val updatedLesson: LessonDto?,
    val lessonsToUpdate: List<LessonDto>,
    val updatedUserCourseProgress: UserCourseProgressDto?,
    val newStreakCount: NewStreakCount?,
    val message: String?
)