package com.testingpractice.duoclonebackend.catalog.app.mapper

import com.testingpractice.duoclonebackend.catalog.api.dto.SectionDto
import com.testingpractice.duoclonebackend.catalog.domain.entity.Section
import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class SectionMapper(
    private val basicMapper: BasicMapper
) {

    fun toDto(section: Section): SectionDto =
        basicMapper.one(section) {
            SectionDto(
                it.id,
                it.title,
                it.courseId,
                it.orderIndex
            )
        }

    fun toDtoList(sections: List<Section>): List<SectionDto> =
        basicMapper.list(sections) { section ->
            toDto(section)
        }
}