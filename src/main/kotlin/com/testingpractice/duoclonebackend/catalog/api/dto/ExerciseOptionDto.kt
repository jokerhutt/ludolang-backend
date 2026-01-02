package com.testingpractice.duoclonebackend.catalog.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ExerciseOptionDto(
    val id: Int?,
    val exerciseId: Int?,
    val content: String?,
    val imageUrl: String?,
    @get:JsonProperty("isCorrect")
    @set:JsonProperty("isCorrect")
    var isCorrect: Boolean?
)