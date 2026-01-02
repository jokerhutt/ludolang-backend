package com.testingpractice.duoclonebackend.controller
import com.testingpractice.duoclonebackend.catalog.api.dto.FlatSectionTreeResponse
import com.testingpractice.duoclonebackend.catalog.api.dto.FlatUnit
import com.testingpractice.duoclonebackend.catalog.domain.entity.Section
import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CatalogIT : AbstractIntegrationTest() {

    @BeforeEach
    fun seed() {
        // intentionally empty
    }

    @Test
    fun `get section tree returns tree`() {

        val section: Section = s1
        val sectionId: Int = section.id!!

        val res = submitGetFlatSectionTree(sectionId)

        assertThat(res).isNotNull
        assertThat(res.units.size).isEqualTo(3)

        for (unit: FlatUnit in res.units) {
            assertThat(unit.lessons).isNotNull
            assertThat(unit.lessons.isEmpty()).isFalse()
        }
    }

    private fun submitGetFlatSectionTree(sectionId: Int): FlatSectionTreeResponse {
        return given()
            .pathParam("sectionId", sectionId)
            .`when`()
            .get(pathConstants.CATALOG + pathConstants.SECTION_TREE)
            .then()
            .statusCode(200)
            .extract()
            .`as`(FlatSectionTreeResponse::class.java)
    }
}