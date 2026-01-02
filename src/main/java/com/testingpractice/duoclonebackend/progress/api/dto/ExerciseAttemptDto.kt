package com.testingpractice.duoclonebackend.progress.api.dto

import java.sql.Timestamp

data class ExerciseAttemptDto(
    val id: Int?,
    val exerciseId: Int?,
    val userId: Int?,
    val isChecked: Boolean,
    val submittedAt: Timestamp?,
    val optionId: Int?,
    val score: Int?
)
