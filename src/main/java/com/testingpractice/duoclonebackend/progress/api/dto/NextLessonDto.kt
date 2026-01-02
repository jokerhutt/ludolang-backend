package com.testingpractice.duoclonebackend.progress.api.dto

import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson

data class NextLessonDto(
    val nextLesson: Lesson?,
    val isCourseCompleted: Boolean
)