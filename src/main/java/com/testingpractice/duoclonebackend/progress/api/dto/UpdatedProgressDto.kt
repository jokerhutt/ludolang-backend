package com.testingpractice.duoclonebackend.progress.api.dto

import com.testingpractice.duoclonebackend.progress.domain.entity.UserCourseProgress

data class UpdatedProgressDto(
    val userCourseProgress: UserCourseProgress?,
    val markedLessonIds: List<Int>
)