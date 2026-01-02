package com.testingpractice.duoclonebackend.service;

import static com.testingpractice.duoclonebackend.testutils.TestConstants.*;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.makeUnit;
import static org.assertj.core.api.Assertions.assertThat;

import com.testingpractice.duoclonebackend.dto.UnitDto;
import com.testingpractice.duoclonebackend.mapper.UnitMapperImpl;
import com.testingpractice.duoclonebackend.repository.UnitRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({UnitServiceImpl.class, UnitMapperImpl.class})
class UnitServiceTest {

  @Autowired private UnitServiceImpl service;
  @Autowired private UnitRepository repo;

  @Test
  void getUnitsByCourse_returnsExpectedDtos() {

    repo.saveAll(
        List.of(
            makeUnit(UNIT_1_TITLE, 1, 1, 1),
            makeUnit(UNIT_2_TITLE, 1, 2, 5),
            makeUnit(UNIT_3_TITLE, 2, 1, 2)));

    List<UnitDto> result = service.getUnitsBySection(1);

    assertThat(result).hasSize(2);
    assertThat(result)
        .extracting(UnitDto::title)
        .containsExactlyInAnyOrder(UNIT_1_TITLE, UNIT_3_TITLE);
  }
}
