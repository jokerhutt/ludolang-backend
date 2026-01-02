package com.testingpractice.duoclonebackend.catalog.infra.repository

import com.testingpractice.duoclonebackend.catalog.domain.entity.LudoUnit
import com.testingpractice.duoclonebackend.catalog.infra.projection.FlatUnitLessonRowProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UnitRepository :
    JpaRepository<LudoUnit, Int> {

    fun findAllBySectionId(sectionId: Int): List<LudoUnit>

    @Query("select unit.id from LudoUnit unit where unit.sectionId = :sectionId")
    fun findAllUnitIdsBySectionId(sectionId: Int): List<Int>

    @Query(
        value = """
            SELECT
              u.id           AS unitId,
              u.order_index  AS unitOrder,
              l.id           AS lessonId,
              l.order_index  AS lessonOrder
            FROM units u
            LEFT JOIN lessons l ON l.unit_id = u.id
            WHERE u.section_id = :sectionId
            ORDER BY u.order_index, l.order_index
        """,
        nativeQuery = true
    )
    fun findFlatUnitLessonRowsBySectionId(
        @Param("sectionId") sectionId: Int
    ): List<FlatUnitLessonRowProjection>

    fun findFirstBySectionIdAndOrderIndexGreaterThanOrderByOrderIndexAsc(
        sectionId: Int,
        orderIndex: Int
    ): LudoUnit?

    fun findFirstBySectionIdOrderByOrderIndexAsc(sectionId: Int): LudoUnit?

    fun findAllBySectionIdOrderByOrderIndexAsc(sectionId: Int): List<LudoUnit>

}