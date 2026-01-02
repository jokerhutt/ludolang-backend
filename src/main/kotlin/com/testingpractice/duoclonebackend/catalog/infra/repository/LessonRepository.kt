package com.testingpractice.duoclonebackend.catalog.infra.repository

import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Collection

interface LessonRepository :
    JpaRepository<Lesson, Int> {

    fun findAllByUnitId(unitId: Int): List<Lesson>

    @Query("select lesson.id from Lesson lesson where lesson.unitId = :unitId")
    fun findAllLessonIdsByUnitId(unitId: Int): List<Int>

    fun findFirstByUnitIdAndOrderIndexGreaterThanOrderByOrderIndexAsc(
        unitId: Int,
        orderIndex: Int
    ): Lesson?

    fun findFirstByUnitIdOrderByOrderIndexAsc(unitId: Int): Lesson?

    fun findAllByUnitIdInOrderByUnitIdAscOrderIndexAsc(
        unitIds: Collection<Int>
    ): List<Lesson>
}