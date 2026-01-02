package com.testingpractice.duoclonebackend.catalog.api.dto

data class UnitDto(
    val id: Int?,
    val title: String?,
    val description: String?,
    val orderIndex: Int?,
    val sectionId: Int?,
    val animationPath: String?,
    val color: String?
)