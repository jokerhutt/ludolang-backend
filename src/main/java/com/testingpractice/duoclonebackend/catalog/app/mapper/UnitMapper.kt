package com.testingpractice.duoclonebackend.catalog.app.mapper
import com.testingpractice.duoclonebackend.catalog.api.dto.UnitDto
import com.testingpractice.duoclonebackend.catalog.domain.entity.LudoUnit
import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import org.springframework.stereotype.Component

@Component
class UnitMapper(
    private val basicMapper: BasicMapper
) {

    fun toDto(ludoUnit: LudoUnit): UnitDto =
        basicMapper.one(ludoUnit) {
            UnitDto(
                it.id,
                it.title,
                it.description,
                it.orderIndex,
                it.sectionId,
                it.animationPath,
                it.color
            )
        }

    fun toDtoList(ludoUnits: List<LudoUnit>): List<UnitDto> =
        basicMapper.list(ludoUnits) { unit ->
            toDto(unit)
        }
}