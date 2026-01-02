package com.testingpractice.duoclonebackend.progress.api.dto

data class ExerciseAttemptResponse(
    val correct: Boolean,
    val score: Int,
    val message: String?,
    val correctResponses: ArrayList<Int>,
    val correctAnswer: String?
)