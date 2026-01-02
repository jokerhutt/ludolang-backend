package com.testingpractice.duoclonebackend.catalog.api.dto

data class LessonDto(
    val id: Int?,
    val title: String?,
    val unitId: Int?,
    val orderIndex: Int?,
    val lessonType: String?,
    val isPassed: Boolean
)