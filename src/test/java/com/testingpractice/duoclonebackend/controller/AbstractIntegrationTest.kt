package com.testingpractice.duoclonebackend.controller;

import com.testingpractice.duoclonebackend.auth.api.dto.AuthUser
import com.testingpractice.duoclonebackend.catalog.domain.entity.Course
import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import com.testingpractice.duoclonebackend.catalog.domain.entity.LudoUnit
import com.testingpractice.duoclonebackend.catalog.domain.entity.Section
import com.testingpractice.duoclonebackend.catalog.infra.repository.CourseRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.ExerciseOptionRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.ExerciseRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.LessonRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.SectionRepository
import com.testingpractice.duoclonebackend.catalog.infra.repository.UnitRepository
import com.testingpractice.duoclonebackend.follow.infra.repository.FollowRepository
import com.testingpractice.duoclonebackend.progress.infra.repository.ExerciseAttemptOptionRepository
import com.testingpractice.duoclonebackend.progress.infra.repository.ExerciseAttemptRepository
import com.testingpractice.duoclonebackend.progress.infra.repository.LessonCompletionRepository
import com.testingpractice.duoclonebackend.progress.infra.repository.UserCourseProgressRepository
import com.testingpractice.duoclonebackend.quest.domain.entity.MonthlyChallengeDefinition
import com.testingpractice.duoclonebackend.quest.domain.entity.QuestDefinition
import com.testingpractice.duoclonebackend.quest.infra.repository.MonthlyChallengeDefinitionRepository
import com.testingpractice.duoclonebackend.quest.infra.repository.QuestDefinitionRepository
import com.testingpractice.duoclonebackend.quest.infra.repository.UserDailyQuestRepository
import com.testingpractice.duoclonebackend.quest.infra.repository.UserMonthlyChallengeRepository
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_1
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_2
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_1_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_2_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_3_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_4_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_5_TITLE
import com.testingpractice.duoclonebackend.testutils.TestConstants.LESSON_6_TITLE
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeCourse
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeExercise
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeExerciseAttempt
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeLesson
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeLessonCompletion
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeMonthlyChallengeDefinition
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeQuestDefinition
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeSection
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUnit
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUser
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUserCourseProgress
import com.testingpractice.duoclonebackend.user.infra.repository.UserRepository
import io.restassured.RestAssured
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import support.Containers
import java.sql.Timestamp
import java.time.Clock
import java.time.ZoneId

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(
        AbstractIntegrationTest.FixedClockConfig::class,
        AbstractIntegrationTest.TestSecurityConfig::class
)
abstract class AbstractIntegrationTest {

  companion object {

    init {
      Containers.MYSQL.isRunning // force startup
    }

    @JvmStatic
    @DynamicPropertySource
    fun mysqlProps(r: DynamicPropertyRegistry) {
      r.add("spring.datasource.url") { Containers.MYSQL.jdbcUrl }
      r.add("spring.datasource.username") { Containers.MYSQL.username }
      r.add("spring.datasource.password") { Containers.MYSQL.password }
      r.add("spring.jpa.hibernate.ddl-auto") { "create-drop" }
    }

    @JvmStatic
    @BeforeAll
    fun restAssuredLogging() {
      RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }
  }

  @LocalServerPort
  protected var port: Int = 0

  @Autowired protected lateinit var unitRepository: UnitRepository
  @Autowired protected lateinit var sectionRepository: SectionRepository
  @Autowired protected lateinit var courseRepository: CourseRepository
  @Autowired protected lateinit var questDefinitionRepository: QuestDefinitionRepository
  @Autowired protected lateinit var exerciseAttemptRepository: ExerciseAttemptRepository
  @Autowired protected lateinit var exerciseRepository: ExerciseRepository
  @Autowired protected lateinit var followRepository: FollowRepository
  @Autowired protected lateinit var exerciseOptionRepository: ExerciseOptionRepository
  @Autowired protected lateinit var exerciseAttemptOptionRepository: ExerciseAttemptOptionRepository
  @Autowired protected lateinit var userDailyQuestRepository: UserDailyQuestRepository
  @Autowired protected lateinit var monthlyChallengeDefinitionRepository: MonthlyChallengeDefinitionRepository
  @Autowired protected lateinit var userMonthlyChallengeRepository: UserMonthlyChallengeRepository
  @Autowired protected lateinit var userRepository: UserRepository
  @Autowired protected lateinit var lessonRepository: LessonRepository
  @Autowired protected lateinit var lessonCompletionRepository: LessonCompletionRepository
  @Autowired protected lateinit var userCourseProgressRepository: UserCourseProgressRepository

  @Autowired
  protected lateinit var jdbc: JdbcTemplate

  protected lateinit var course1: Course
  protected lateinit var l1: Lesson
  protected lateinit var l2: Lesson
  protected lateinit var l3: Lesson
  protected lateinit var l4: Lesson
  protected lateinit var l5: Lesson
  protected lateinit var l6: Lesson

  protected lateinit var s1: Section

  protected lateinit var u1: LudoUnit
  protected lateinit var u2: LudoUnit
  protected lateinit var u3: LudoUnit

  protected lateinit var monthlyChallengeDefinition: MonthlyChallengeDefinition
  protected lateinit var questDefinitions: List<QuestDefinition>

  @BeforeEach
  fun restAssuredBase() {
    RestAssured.baseURI = "http://localhost"
    RestAssured.port = port
    initializeCoursesSectionsAndLessons()
  }

  @TestConfiguration(proxyBeanMethods = false)
  @Profile("test")
  open class FixedClockConfig {
    @Bean
    open fun clock(): Clock =
            Clock.fixed(FIXED_TIMESTAMP_2.toInstant(), ZoneId.systemDefault())
  }

  @TestConfiguration(proxyBeanMethods = false)
  @Profile("test")
  open class TestSecurityConfig {
    @Bean
    open fun testFilter(http: HttpSecurity): SecurityFilterChain {
      http
              .csrf { it.disable() }
                .authorizeHttpRequests { it.anyRequest().authenticated() }
          .addFilterBefore(
              { req, res, chain ->
                  val request = req as HttpServletRequest
                  val response = res as HttpServletResponse

                  val uid = request.getHeader("X-Test-User-Id")?.toInt() ?: 1

                  val auth = UsernamePasswordAuthenticationToken(
                      AuthUser(uid),
                      null,
                      emptyList()
                  )
                  auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                  SecurityContextHolder.getContext().authentication = auth

                  chain.doFilter(request, response)
              },
              UsernamePasswordAuthenticationFilter::class.java
          )

      return http.build()
    }
  }

  protected fun initializeCoursesSectionsAndLessons() {

    course1 = courseRepository.save(makeCourse("Course 1", "defaultImg"))

    s1 = sectionRepository.save(makeSection("Section 1", course1.id!!, 1))
    val s1Id = s1.id!!

            val units = unitRepository.saveAll(
            listOf(
                    makeUnit("Unit 1", course1.id!!, s1Id, 1),
    makeUnit("Unit 2", course1.id!!, s1Id, 2),
    makeUnit("Unit 3", course1.id!!, s1Id, 3)
            )
        )

    u1 = units[0]
    u2 = units[1]
    u3 = units[2]

    val savedLessons = lessonRepository.saveAll(
            listOf(
                    makeLesson(LESSON_1_TITLE, u1.id!!, 1, "Exercise"),
    makeLesson(LESSON_2_TITLE, u1.id!!, 2, "Exercise"),
    makeLesson(LESSON_3_TITLE, u2.id!!, 1, "Exercise"),
    makeLesson(LESSON_4_TITLE, u2.id!!, 2, "Exercise"),
    makeLesson(LESSON_5_TITLE, u3.id!!, 1, "Exercise"),
    makeLesson(LESSON_6_TITLE, u3.id!!, 2, "Exercise")
            )
        )

    l1 = savedLessons[0]
    l2 = savedLessons[1]
    l3 = savedLessons[2]
    l4 = savedLessons[3]
    l5 = savedLessons[4]
    l6 = savedLessons[5]

    questDefinitions = questDefinitionRepository.saveAll(
            listOf(
                    makeQuestDefinition("STREAK", 1, 10, true),
                    makeQuestDefinition("ACCURACY", 2, 10, true),
                    makeQuestDefinition("PERFECT", 1, 10, true)
            )
    )

    monthlyChallengeDefinition =
            monthlyChallengeDefinitionRepository.save(
                    makeMonthlyChallengeDefinition("COMPLETE_QUESTS", 30, 200, true)
            )
  }

  protected fun setupUserCompletionForTest(
          correctScores: Int,
          courseId: Int,
          submittedLessonId: Int,
          currentLessonId: Int,
          completedLessonsCount: Int,
          usersPoints: Int,
          streakLength: Int,
          lastSubmission: Timestamp,
          attemptTime: Timestamp
  ): Int {

    val user =
            userRepository.save(
                    makeUser(
                            courseId,
                            "testuser",
                            "test",
                            "user",
                            "emailOne",
                            "default",
                            usersPoints,
                            FIXED_TIMESTAMP_1,
                            lastSubmission,
                            streakLength
                    )
            )

    val userId = user.id!!

            userCourseProgressRepository.save(
                    makeUserCourseProgress(
                            userId,
                            courseId,
                            false,
                            currentLessonId,
                            FIXED_TIMESTAMP_1
                    )
            )

    val savedExercises = exerciseRepository.saveAll(
            listOf(
                    makeExercise(submittedLessonId, "Translate", 1),
                    makeExercise(submittedLessonId, "Translate", 2),
                    makeExercise(submittedLessonId, "Translate", 3)
            )
    )

    val exerciseIds = savedExercises.map { it.id!! }

    val completedLessons =
            listOf(l1, l2, l3, l4, l5, l6).map { it.id!! }

    repeat(3) { i ->
            val score = if (i < correctScores) 5 else 0
      exerciseAttemptRepository.save(
              makeExerciseAttempt(
                      exerciseIds[i],
                      userId,
                      false,
                      attemptTime,
                      i + 1,
                      score
              )
      )
    }

    repeat(completedLessonsCount) { i ->
            lessonCompletionRepository.save(
                    makeLessonCompletion(userId, completedLessons[i], course1.id!!, 10)
            )
    }

    return userId
  }
}