package com.testingpractice.duoclonebackend.progress.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson

data class NextLessonDto(
    val nextLesson: Lesson?,

    @get:JsonProperty("isPassed")
    @set:JsonProperty("isCourseCompleted")
    var isCourseCompleted: Boolean
)