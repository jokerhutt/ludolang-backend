package com.testingpractice.duoclonebackend.progress.api.dto

data class LessonCompleteRequest(
    val lessonId: Int,
    val courseId: Int
)