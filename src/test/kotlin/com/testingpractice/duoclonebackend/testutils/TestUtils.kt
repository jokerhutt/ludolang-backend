package com.testingpractice.duoclonebackend.testutils;

import com.testingpractice.duoclonebackend.catalog.domain.entity.Course
import com.testingpractice.duoclonebackend.catalog.domain.entity.Exercise
import com.testingpractice.duoclonebackend.catalog.domain.entity.Lesson
import com.testingpractice.duoclonebackend.catalog.domain.entity.LudoUnit
import com.testingpractice.duoclonebackend.catalog.domain.entity.Section
import com.testingpractice.duoclonebackend.follow.domain.entity.Follow
import com.testingpractice.duoclonebackend.progress.domain.entity.ExerciseAttempt
import com.testingpractice.duoclonebackend.progress.domain.entity.LessonCompletion
import com.testingpractice.duoclonebackend.progress.domain.entity.UserCourseProgress
import com.testingpractice.duoclonebackend.progress.domain.entity.embeddable.LessonCompletionId
import com.testingpractice.duoclonebackend.quest.domain.entity.MonthlyChallengeDefinition
import com.testingpractice.duoclonebackend.quest.domain.entity.QuestDefinition
import com.testingpractice.duoclonebackend.quest.domain.entity.UserDailyQuest
import com.testingpractice.duoclonebackend.quest.domain.entity.UserMonthlyChallenge
import com.testingpractice.duoclonebackend.quest.domain.entity.embeddable.UserDailyQuestId
import com.testingpractice.duoclonebackend.quest.domain.entity.embeddable.UserMonthlyChallengeId
import com.testingpractice.duoclonebackend.user.domain.entity.User
import java.sql.Timestamp
import java.time.LocalDate

object TestUtils {

fun makeUnit(
        title: String,
        courseId: Int,
        sectionId: Int,
        orderIndex: Int
) = LudoUnit(
        id = null,
        title = title,
        description = "Default description",
        orderIndex = orderIndex,
        courseId = courseId,
        sectionId = sectionId,
        animationPath = "Default animation path",
        color = "GREEN"
)

        fun makeQuestDefinition(
                code: String,
                target: Int,
                rewardPoints: Int,
                active: Boolean
        ) = QuestDefinition(
                id = null,
                code = code,
                target = target,
                rewardPoints = rewardPoints,
                active = active
        )

        fun makeMonthlyChallengeDefinition(
                code: String,
                target: Int,
                rewardPoints: Int,
                active: Boolean
        ) = MonthlyChallengeDefinition(
                id = null,
                code = code,
                target = target,
                rewardPoints = rewardPoints,
                active = active
        )

fun makeUser(
        currentCourseId: Int?,
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        pfpSrc: String,
        points: Int = 0,
        createdAt: Timestamp,
        lastSubmission: Timestamp?,
        streakLength: Int = 0
) = User(
        id = null,
        currentCourseId = currentCourseId,
        username = username,
        firstName = firstName,
        lastName = lastName,
        email = email,
        pfpSrc = pfpSrc,
        points = points,
        createdAt = createdAt,
        lastSubmission = lastSubmission,
        streakLength = streakLength
)

fun makeFollow(
        followerId: Int,
        followedId: Int,
        createdAt: Timestamp
) = Follow(
        id = null,
        followerId = followerId,
        followedId = followedId,
        createdAt = createdAt
)

fun makeUserMonthlyChallenge(
        userId: Int,
        defId: Int,
        today: LocalDate,
        progress: Int
) = UserMonthlyChallenge(
        id = UserMonthlyChallengeId(
                userId = userId,
                challengeDefId = defId,
                year = today.year,
                month = today.monthValue
        ),
        progress = progress,
        rewardClaimed = false,
        completedAt = null
)

fun makeUserDailyQuest(
        userId: Int,
        defId: Int,
        today: LocalDate,
        progress: Int
) = UserDailyQuest(
        id = UserDailyQuestId(
                userId = userId,
                questDefId = defId,
                date = today
        ),
        progress = progress,
        rewardClaimed = false,
        completedAt = null
)

fun makeCourse(
        title: String,
        imgSrc: String = "Default"
) = Course(
        id = null,
        title = title,
        imgSrc = imgSrc
)

fun makeUserCourseProgress(
        userId: Int,
        courseId: Int,
        isComplete: Boolean,
        currentLessonId: Int?,
        updatedAt: Timestamp
) = UserCourseProgress(
        id = null,
        userId = userId,
        courseId = courseId,
        isComplete = isComplete,
        currentLessonId = currentLessonId,
        updatedAt = updatedAt
)

fun makeSection(
        title: String,
        courseId: Int,
        orderIndex: Int
) = Section(
        id = null,
        title = title,
        courseId = courseId,
        orderIndex = orderIndex
)

fun makeExercise(
        lessonId: Int,
        prompt: String,
        orderIndex: Int
) = Exercise(
        id = null,
        lessonId = lessonId,
        prompt = prompt,
        type = "CLOZE",
        orderIndex = orderIndex
)

fun makeExerciseAttempt(
        exerciseId: Int,
        userId: Int,
        isChecked: Boolean,
        submittedAt: Timestamp,
        optionId: Int?,
        score: Int?
) = ExerciseAttempt(
        id = null,
        exerciseId = exerciseId,
        userId = userId,
        isChecked = isChecked,
        submittedAt = submittedAt,
        optionId = optionId,
        score = score
)

fun makeLesson(
        title: String,
        unitId: Int,
        orderIndex: Int,
        lessonType: String
) = Lesson(
        id = null,
        title = title,
        unitId = unitId,
        lessonType = lessonType,
        orderIndex = orderIndex
)

fun makeLessonCompletion(
        userId: Int,
        lessonId: Int,
        courseId: Int,
        score: Int
) = LessonCompletion(
        id = LessonCompletionId(userId, lessonId),
        courseId = courseId,
        score = score,
        completedAt = Timestamp(System.currentTimeMillis())
)
        }