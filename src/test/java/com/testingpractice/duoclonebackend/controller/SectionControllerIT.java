package com.testingpractice.duoclonebackend.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.testingpractice.duoclonebackend.commons.constants.pathConstants;
import java.util.List;

import com.testingpractice.duoclonebackend.dto.UnitDto;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SectionControllerIT extends AbstractIntegrationTest {

  @BeforeEach
  void seed() {
  }

  @Test
  void getUnitsBySection_returnsUnitsForThatSection() {
    List<UnitDto> response = submitGetUnitsBySection();
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(3);

    for (UnitDto unit : response) {
      assertThat(unit.sectionId()).isEqualTo(s1.getId());
    }


  }

  private List<UnitDto> submitGetUnitsBySection() {
    return given()
            .contentType("application/json")
            .when()
            .get(pathConstants.SECTIONS + pathConstants.GET_UNITS_BY_SECTION.replace("{sectionId}", s1.getId().toString()))
            .then()
            .statusCode(200)
            .extract()
            .as(new TypeRef<List<UnitDto>>() {});
  }


}
