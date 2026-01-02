package com.testingpractice.duoclonebackend.catalog.api.dto

data class SectionTreeNode(
    val section: SectionDto,
    val units: List<UnitTreeNode>
)

data class UnitTreeNode(
    val unit: UnitDto,
    val lessons: List<LessonDto>
)