package com.testingpractice.duoclonebackend.catalog.api.controller

import com.testingpractice.duoclonebackend.catalog.api.dto.SectionDto
import com.testingpractice.duoclonebackend.catalog.api.dto.SectionTreeNode
import com.testingpractice.duoclonebackend.catalog.api.dto.UnitDto
import com.testingpractice.duoclonebackend.catalog.app.service.SectionService
import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.progress.app.service.UnitService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.SECTIONS)
class SectionController(
    private val sectionService: SectionService,
    private val unitService: UnitService
) {

    @GetMapping(pathConstants.GET_SECTIONS_FROM_IDS)
    fun getSectionsByIds(@RequestParam sectionIds: List<Int>): List<SectionDto> {
        return sectionService.getSectionsByIds(sectionIds)
    }

    @GetMapping(pathConstants.GET_UNITS_BY_SECTION)
    fun getUnitsBySection(@PathVariable sectionId: Int): List<UnitDto> {
        return unitService.getUnitsBySection(sectionId)
    }

    @GetMapping(pathConstants.GET_UNIT_IDS_BY_SECTION)
    fun getUnitIdsBySection(@PathVariable sectionId: Int): List<Int> {
        return unitService.getUnitIdsBySection(sectionId)
    }

    @GetMapping(pathConstants.GET_BULK_SECTIONS)
    fun getBulkSection(
        @PathVariable("sectionId") sectionId: Int,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): SectionTreeNode {
        return sectionService.getBulkSection(sectionId, userId)
    }
}