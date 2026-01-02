package com.testingpractice.duoclonebackend.service;

import static com.testingpractice.duoclonebackend.testutils.TestConstants.*;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.makeLesson;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.makeLessonCompletion;
import static org.assertj.core.api.Assertions.assertThat;

import com.testingpractice.duoclonebackend.dto.LessonDto;
import com.testingpractice.duoclonebackend.entity.Lesson;
import com.testingpractice.duoclonebackend.mapper.LessonMapperImpl;
import com.testingpractice.duoclonebackend.repository.LessonCompletionRepository;
import com.testingpractice.duoclonebackend.repository.LessonRepository;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({LessonServiceImpl.class, LessonMapperImpl.class})
public class LessonServiceTest {

  @Autowired private LessonServiceImpl service;
  @Autowired private LessonRepository repo;
  @Autowired private LessonCompletionRepository lessonCompletionRepository;

  private Lesson l1;
  private Lesson l2;
  private Lesson l3;
  private Lesson l4;

  @BeforeEach
  void setUp() {
    repo.deleteAll();
    lessonCompletionRepository.deleteAll();

    List<Lesson> savedLessons = repo.saveAll(List.of(
            makeLesson(LESSON_1_TITLE, 2, 1, "Exercise"),
            makeLesson(LESSON_2_TITLE, 2, 2, "Exercise"),
            makeLesson(LESSON_3_TITLE, 2, 3, "Exercise"),
            makeLesson(LESSON_4_TITLE, 1, 3, "Exercise")
    ));

    l1 = savedLessons.get(0);
    l2 = savedLessons.get(1);
    l3 = savedLessons.get(2);
    l4 = savedLessons.get(3);

    lessonCompletionRepository.saveAll(List.of(
            makeLessonCompletion(1, l1.getId(), 1, 15),
            makeLessonCompletion(1, l2.getId(), 1, 20),
            makeLessonCompletion(1, l4.getId(), 1, 10)
    ));
  }

  @Test
  void getLessonsByUnit_returnsExpectedDtos() {

    //Should only have one because l3 is not passed and l4 is not in unit 2
    List<LessonDto> result = service.getLessonsByUnit(2, 1);
    assertThat(result).hasSize(3);
    assertThat(result)
            .extracting(LessonDto::isPassed)
            .filteredOn(completed -> !completed)
            .hasSize(1);

  }




}
