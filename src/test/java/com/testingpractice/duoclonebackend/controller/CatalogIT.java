package com.testingpractice.duoclonebackend.controller;

import com.testingpractice.duoclonebackend.commons.constants.pathConstants;
import com.testingpractice.duoclonebackend.dto.FlatTree.FlatSectionTreeResponse;
import com.testingpractice.duoclonebackend.dto.FlatTree.FlatUnit;
import com.testingpractice.duoclonebackend.entity.Section;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CatalogIT extends AbstractIntegrationTest {

    @BeforeEach
    void seed () {

    }


    @Test
    public void getSectionTree_returnsTree () {

        Section section = s1;
        Integer sectionId = section.getId();

        FlatSectionTreeResponse res = submitGetFlatSectionTree(sectionId);

        assertThat(res).isNotNull();
        assertThat(res.units().size()).isEqualTo(3);

        for (FlatUnit unit : res.units()) {
            assertThat(unit.lessons()).isNotNull();
            assertThat(unit.lessons().isEmpty()).isFalse();
        }




    }

    private FlatSectionTreeResponse submitGetFlatSectionTree(Integer sectionId) {
        return given()
                .pathParam("sectionId", sectionId)
                .when()
                .get(pathConstants.CATALOG + pathConstants.SECTION_TREE)
                .then()
                .statusCode(200)
                .extract()
                .as(FlatSectionTreeResponse.class);
    }


}
