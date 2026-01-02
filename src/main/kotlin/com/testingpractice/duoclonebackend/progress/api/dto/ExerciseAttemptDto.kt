package com.testingpractice.duoclonebackend.progress.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.sql.Timestamp

data class ExerciseAttemptDto(
    val id: Int?,
    val exerciseId: Int?,
    val userId: Int?,
    @get:JsonProperty("isChecked")
    @set:JsonProperty("isChecked")
    var isChecked: Boolean,
    val submittedAt: Timestamp?,
    val optionId: Int?,
    val score: Int?
)
