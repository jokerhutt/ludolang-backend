package com.testingpractice.duoclonebackend.catalog.api.dto

data class FlatLesson(
    val id: Int?,
    val orderIndex: Int?
)

data class FlatSectionTreeResponse(
    val sectionId: Int?,
    val units: List<FlatUnit>
)

data class FlatUnit(
    val id: Int?,
    val orderIndex: Int?,
    val lessons: List<FlatLesson>
)