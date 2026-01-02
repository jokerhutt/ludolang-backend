package com.testingpractice.duoclonebackend.service

import com.testingpractice.duoclonebackend.catalog.api.dto.UnitDto
import com.testingpractice.duoclonebackend.catalog.app.mapper.UnitMapper
import com.testingpractice.duoclonebackend.catalog.infra.repository.UnitRepository
import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import com.testingpractice.duoclonebackend.progress.app.service.UnitService
import com.testingpractice.duoclonebackend.testutils.TestConstants.UNIT_1_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.UNIT_2_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.UNIT_3_TITLE
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUnit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(UnitService::class, BasicMapper::class, UnitMapper::class)
open class UnitServiceTest {

    @Autowired
    lateinit var service: UnitService

    @Autowired
    lateinit var repo: UnitRepository

    @Test
    fun getUnitsByCourse_returnsExpectedDtos() {

        repo.saveAll(
            listOf(
                makeUnit(UNIT_1_TITLE, 1, 1, 1),
                makeUnit(UNIT_2_TITLE, 1, 2, 5),
                makeUnit(UNIT_3_TITLE, 2, 1, 2)
            )
        )

        val result: List<UnitDto> = service.getUnitsBySection(1)

        assertThat(result).hasSize(2)
        assertThat(result)
            .extracting<String> { it.title!! }
            .containsExactlyInAnyOrder(UNIT_1_TITLE, UNIT_3_TITLE)
    }
}