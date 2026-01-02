package com.testingpractice.duoclonebackend.progress.api.dto

data class ExerciseAttemptRequest(
    val exerciseId: Int,
    val optionIds: ArrayList<Int>,
    val userId: Int
)