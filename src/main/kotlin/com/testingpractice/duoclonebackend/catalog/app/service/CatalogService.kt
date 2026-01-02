package com.testingpractice.duoclonebackend.catalog.app.service

import com.testingpractice.duoclonebackend.catalog.api.dto.FlatSectionTreeResponse
import com.testingpractice.duoclonebackend.catalog.app.mapper.FlatSectionTreeMapper
import com.testingpractice.duoclonebackend.catalog.infra.repository.UnitRepository
import org.springframework.stereotype.Service

@Service
class CatalogService(
    private val unitRepository: UnitRepository,
    private val flatSectionTreeMapper: FlatSectionTreeMapper
) {

    fun getFlatCourseTree(sectionId: Int): FlatSectionTreeResponse {
        val rows = unitRepository.findFlatUnitLessonRowsBySectionId(sectionId)
        return flatSectionTreeMapper.toFlatTree(sectionId, rows)
    }
}