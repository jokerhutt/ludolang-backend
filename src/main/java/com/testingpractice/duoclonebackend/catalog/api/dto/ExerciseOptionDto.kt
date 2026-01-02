package com.testingpractice.duoclonebackend.catalog.api.dto

data class ExerciseOptionDto(
    val id: Int?,
    val exerciseId: Int?,
    val content: String?,
    val imageUrl: String?,
    val isCorrect: Boolean?
)