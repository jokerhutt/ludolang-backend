package com.testingpractice.duoclonebackend.progress.app.service

import com.testingpractice.duoclonebackend.catalog.api.dto.UnitDto
import com.testingpractice.duoclonebackend.catalog.app.mapper.UnitMapper
import com.testingpractice.duoclonebackend.catalog.infra.repository.UnitRepository
import org.springframework.stereotype.Service

@Service
class UnitService(
    private val unitRepository: UnitRepository,
    private val unitMapper: UnitMapper
) {

    fun getUnitsBySection(sectionId: Int): List<UnitDto> {
        val units = unitRepository.findAllBySectionId(sectionId)
        return unitMapper.toDtoList(units)
    }

    fun getUnitsByIds(unitIds: List<Int>): List<UnitDto> {
        val units = unitRepository.findAllById(unitIds)
        return unitMapper.toDtoList(units)
    }

    fun getUnitIdsBySection(sectionId: Int): List<Int> {
        return unitRepository.findAllUnitIdsBySectionId(sectionId)
    }
}