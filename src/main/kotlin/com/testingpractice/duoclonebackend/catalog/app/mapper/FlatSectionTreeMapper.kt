package com.testingpractice.duoclonebackend.catalog.app.mapper

import com.testingpractice.duoclonebackend.catalog.api.dto.FlatLesson
import com.testingpractice.duoclonebackend.catalog.api.dto.FlatSectionTreeResponse
import com.testingpractice.duoclonebackend.catalog.api.dto.FlatUnit
import com.testingpractice.duoclonebackend.catalog.infra.projection.FlatUnitLessonRowProjection
import org.springframework.stereotype.Component

@Component
class FlatSectionTreeMapper {

    fun toFlatTree(
        sectionId: Int?,
        rows: List<FlatUnitLessonRowProjection>
    ): FlatSectionTreeResponse {

        val byUnit: Map<Int, List<FlatUnitLessonRowProjection>> =
            rows.groupBy { it.getUnitId()!! }

        val units: List<FlatUnit> =
            byUnit.values
                .sortedBy { it[0].getUnitOrder() }
                .map { mapFlatUnit(it) }

        return FlatSectionTreeResponse(sectionId, units)
    }

    private fun mapFlatUnit(
        group: List<FlatUnitLessonRowProjection>
    ): FlatUnit {

        val head = group[0]

        val lessons: List<FlatLesson> =
            group
                .filter { it.getLessonId() != null }
                .sortedBy { it.getLessonOrder() }
                .map { mapFlatLesson(it) }

        return FlatUnit(
            head.getUnitId(),
            head.getUnitOrder(),
            lessons
        )
    }

    private fun mapFlatLesson(
        row: FlatUnitLessonRowProjection
    ): FlatLesson =
        FlatLesson(
            row.getLessonId(),
            row.getLessonOrder()
        )
}