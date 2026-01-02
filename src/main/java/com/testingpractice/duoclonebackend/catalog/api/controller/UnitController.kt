package com.testingpractice.duoclonebackend.catalog.api.controller

import com.testingpractice.duoclonebackend.catalog.api.dto.LessonDto
import com.testingpractice.duoclonebackend.catalog.api.dto.UnitDto
import com.testingpractice.duoclonebackend.catalog.app.service.LessonService
import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.progress.app.service.UnitService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.UNITS)
class UnitController(
    private val lessonService: LessonService,
    private val unitService: UnitService
) {

    @GetMapping(pathConstants.GET_LESSONS_BY_UNIT)
    fun getLessonsByUnit(
        @PathVariable unitId: Int,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): List<LessonDto> {
        return lessonService.getLessonsByUnit(unitId, userId)
    }

    @GetMapping(pathConstants.GET_UNITS_FROM_IDS)
    fun getUnitsByIds(@RequestParam unitIds: List<Int>): List<UnitDto> {
        return unitService.getUnitsByIds(unitIds)
    }

    @GetMapping(pathConstants.GET_LESSON_IDS_BY_UNIT)
    fun getLessonIdsByUnit(@PathVariable unitId: Int): List<Int> {
        return lessonService.getLessonIdsByUnit(unitId)
    }
}