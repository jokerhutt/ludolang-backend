package com.testingpractice.duoclonebackend.catalog.infra.repository

import com.testingpractice.duoclonebackend.catalog.domain.entity.Section
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SectionRepository :
    JpaRepository<Section, Int> {

    fun findByIdIn(sectionIds: List<Int>): List<Section>

    @Query("select section.id from Section section where section.courseId = :courseId")
    fun findAllSectionIdsByCourseId(courseId: Int): List<Int>

    fun findFirstByCourseIdAndOrderIndexGreaterThanOrderByOrderIndexAsc(
        courseId: Int,
        orderIndex: Int
    ): Section?

    fun findFirstByCourseIdOrderByOrderIndexAsc(courseId: Int): Section?
}