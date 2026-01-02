package com.testingpractice.duoclonebackend.catalog.api.controller

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.dto.FlatTree.FlatSectionTreeResponse
import com.testingpractice.duoclonebackend.service.CatalogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.CATALOG)
class CatalogController(
    private val catalogService: CatalogService
) {

    @GetMapping(pathConstants.SECTION_TREE)
    fun getSectionTree(@PathVariable sectionId: Int): ResponseEntity<FlatSectionTreeResponse> {
        return ResponseEntity.ok(catalogService.getFlatCourseTree(sectionId))
    }
}