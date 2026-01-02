package com.testingpractice.duoclonebackend.progress.api.dto

data class UserCourseProgressDto(
    val id: Int?,
    val userId: Int?,
    val courseId: Int?,
    val sectionId: Int?,
    val isComplete: Boolean?,
    val currentLessonId: Int?,
    val completedLessons: Int?
)