package com.testingpractice.duoclonebackend.catalog.api.dto

data class ExerciseDto(
    val id: Int?,
    val lessonId: Int?,
    val prompt: String?,
    val type: String?,
    val orderIndex: Int?,
    val options: List<ExerciseOptionDto>
)