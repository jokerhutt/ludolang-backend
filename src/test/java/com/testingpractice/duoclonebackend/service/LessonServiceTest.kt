package com.testingpractice.duoclonebackend.service
import com.testingpractice.duoclonebackend.catalog.api.dto.LessonDto
import com.testingpractice.duoclonebackend.catalog.app.mapper.LessonMapper
import com.testingpractice.duoclonebackend.catalog.app.service.LessonService
import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import com.testingpractice.duoclonebackend.catalog.infra.repository.LessonRepository
import com.testingpractice.duoclonebackend.commons.mapper.BasicMapper
import com.testingpractice.duoclonebackend.progress.infra.repository.LessonCompletionRepository
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_1_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_2_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_3_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_4_TITLE
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeLesson
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeLessonCompletion
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(LessonService::class, BasicMapper::class, LessonMapper::class)
open class LessonServiceTest {

    @Autowired
    lateinit var service: LessonService

    @Autowired
    lateinit var repo: LessonRepository

    @Autowired
    lateinit var lessonCompletionRepository: LessonCompletionRepository

    lateinit var l1: Lesson
    lateinit var l2: Lesson
    lateinit var l3: Lesson
    lateinit var l4: Lesson

    @BeforeEach
    fun setUp() {
        repo.deleteAll()
        lessonCompletionRepository.deleteAll()

        val savedLessons = repo.saveAll(
            listOf(
                makeLesson(LESSON_1_TITLE, 2, 1, "Exercise"),
                makeLesson(LESSON_2_TITLE, 2, 2, "Exercise"),
                makeLesson(LESSON_3_TITLE, 2, 3, "Exercise"),
                makeLesson(LESSON_4_TITLE, 1, 3, "Exercise")
            )
        )

        l1 = savedLessons[0]
        l2 = savedLessons[1]
        l3 = savedLessons[2]
        l4 = savedLessons[3]

        lessonCompletionRepository.saveAll(
            listOf(
                makeLessonCompletion(1, l1.id!!, 1, 15),
                makeLessonCompletion(1, l2.id!!, 1, 20),
                makeLessonCompletion(1, l4.id!!, 1, 10)
            )
        )
    }

    @Test
    fun getLessonsByUnit_returnsExpectedDtos() {

        // Should only have one because l3 is not passed and l4 is not in unit 2
        val result: List<LessonDto> = service.getLessonsByUnit(2, 1)

        assertThat(result).hasSize(3)
        assertThat(result)
            .extracting<Boolean> { it.isPassed }
            .filteredOn { completed -> !completed }
            .hasSize(1)
    }
}