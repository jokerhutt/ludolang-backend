package com.testingpractice.duoclonebackend.catalog.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LessonDto(
    val id: Int?,
    val title: String?,
    val unitId: Int?,
    val orderIndex: Int?,
    val lessonType: String?,
    @get:JsonProperty("isPassed")
    @set:JsonProperty("isPassed")
    var isPassed: Boolean
)