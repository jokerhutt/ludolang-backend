package com.testingpractice.duoclonebackend.controller

import com.testingpractice.duoclonebackend.catalog.api.dto.UnitDto
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import io.restassured.common.mapper.TypeRef
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import com.testingpractice.duoclonebackend.commons.constants.pathConstants

class SectionControllerIT : AbstractIntegrationTest() {

    @BeforeEach
    fun seed() {
    }

    @Test
    fun getUnitsBySection_returnsUnitsForThatSection() {

        val response = submitGetUnitsBySection()

        assertThat(response).isNotNull
        assertThat(response.size).isEqualTo(3)

        for (unit in response) {
            assertThat(unit.sectionId).isEqualTo(s1.id)
        }
    }

    private fun submitGetUnitsBySection(): List<UnitDto> {
        return given()
            .contentType("application/json")
            .`when`()
            .get(
                pathConstants.SECTIONS +
                        pathConstants.GET_UNITS_BY_SECTION.replace(
                            "{sectionId}",
                            s1.id.toString()
                        )
            )
            .then()
            .statusCode(200)
            .extract()
            .`as`(object : TypeRef<List<UnitDto>>() {})
    }
}