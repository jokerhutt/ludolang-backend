package com.testingpractice.duoclonebackend.catalog.app.service

import com.testingpractice.duoclonebackend.catalog.api.dto.SectionDto
import com.testingpractice.duoclonebackend.catalog.api.dto.SectionTreeNode
import com.testingpractice.duoclonebackend.catalog.api.dto.UnitTreeNode
import com.testingpractice.duoclonebackend.catalog.app.mapper.LessonMapper
import com.testingpractice.duoclonebackend.catalog.app.mapper.SectionMapper
import com.testingpractice.duoclonebackend.catalog.app.mapper.UnitMapper
import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import com.testingpractice.duoclonebackend.catalog.infra.repository.LessonRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.SectionRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.UnitRepository
import com.testingpractice.duoclonebackend.commons.exception.ApiException
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import org.springframework.stereotype.Service
import java.util.Collection

@Service
class SectionService(
    private val sectionRepository: SectionRepository,
    private val sectionMapper: SectionMapper,
    private val unitRepository: UnitRepository,
    private val unitMapper: UnitMapper,
    private val lessonRepository: LessonRepository,
    private val lessonMapper: LessonMapper,
    private val lessonService: LessonService
) {

    fun getSectionsByIds(sectionIds: List<Int>): List<SectionDto> {
        val sections = sectionRepository.findAllById(sectionIds)
        return sectionMapper.toDtoList(sections)
    }

    fun getSectionIdsByCourse(courseId: Int): List<Int> {
        return sectionRepository.findAllSectionIdsByCourseId(courseId)
    }

    fun getBulkSection(sectionId: Int, userId: Int): SectionTreeNode {
        val section = sectionRepository.findById(sectionId).orElse(null)
            ?: throw ApiException(ErrorCode.SECTION_NOT_FOUND)

        val sectionDto = sectionMapper.toDto(section)

        val units = unitRepository.findAllBySectionIdOrderByOrderIndexAsc(sectionId)
        val unitIds = units.mapNotNull { it.id }

        val lessons =
            lessonRepository.findAllByUnitIdInOrderByUnitIdAscOrderIndexAsc(unitIds as Collection<Int>)

        val lessonsByUnit: Map<Int, List<Lesson>> =
            lessons.groupBy { it.unitId!! }

        val unitNodes =
            units.map { unit ->
                val unitLessons = lessonsByUnit[unit.id] ?: emptyList()
                UnitTreeNode(
                    unitMapper.toDto(unit),
                    lessonMapper.toDtoList(
                        unitLessons,
                        lessonService.completedSetFor(
                            userId,
                            unitLessons.mapNotNull { it.id }
                        )
                    )
                )
            }

        return SectionTreeNode(sectionDto, unitNodes)
    }
}