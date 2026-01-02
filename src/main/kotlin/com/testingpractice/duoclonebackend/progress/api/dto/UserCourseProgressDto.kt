package com.testingpractice.duoclonebackend.progress.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCourseProgressDto(
    val id: Int?,
    val userId: Int?,
    val courseId: Int?,
    val sectionId: Int?,
    @get:JsonProperty("isComplete")
    @set:JsonProperty("isComplete")
    var isComplete: Boolean?,
    val currentLessonId: Int?,
    val completedLessons: Int?
)