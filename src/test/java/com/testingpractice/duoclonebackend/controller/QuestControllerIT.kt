package com.testingpractice.duoclonebackend.controller

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.quest.api.dto.QuestResponse
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_DATE_1
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_DATE_2
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_DATE_2_1
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_1
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_2
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUser
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUserDailyQuest
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUserMonthlyChallenge
import io.restassured.common.mapper.TypeRef
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class QuestControllerIT : AbstractIntegrationTest() {

    @BeforeEach
    fun seed() {
    }

    @Test
    fun getUserDailyQuest_noneExisting_createsAndReturnsNew() {

        val user =
            userRepository.save(
                makeUser(
                    course1.id,
                    "testUser",
                    "test",
                    "user",
                    "testemail",
                    "pfp",
                    0,
                    FIXED_TIMESTAMP_1,
                    FIXED_TIMESTAMP_2,
                    0
                )
            )

        val userId = user.id!!

        userMonthlyChallengeRepository.save(
            makeUserMonthlyChallenge(
                userId,
                monthlyChallengeDefinition.id!!,
                FIXED_DATE_2_1,
                2
            )
        )

        val response = submitUserDailyQuestProgressRequest(userId)

        assertThat(response).isNotEmpty

        for (res in response) {
            assertThat(res.active).isEqualTo(true)
            assertThat(res.progress).isEqualTo(0)
        }
    }

    @Test
    fun getUserDailyQuest_nextDay_returnsNew() {

        val user =
            userRepository.save(
                makeUser(
                    course1.id,
                    "testUser",
                    "test",
                    "user",
                    "testemail",
                    "pfp",
                    0,
                    FIXED_TIMESTAMP_1,
                    FIXED_TIMESTAMP_2,
                    0
                )
            )

        val userId = user.id

        userMonthlyChallengeRepository.save(
            makeUserMonthlyChallenge(
                userId!!,
                monthlyChallengeDefinition.id!!,
                FIXED_DATE_1,
                2
            )
        )

        for (i in questDefinitions.indices) {
            userDailyQuestRepository.save(
                makeUserDailyQuest(
                    userId,
                    questDefinitions[i].id!!,
                    FIXED_DATE_1,
                    1
                )
            )
        }

        val response = submitUserDailyQuestProgressRequest(userId)

        assertThat(response).isNotEmpty

        for (res in response) {
            assertThat(res.active).isEqualTo(true)
            assertThat(res.progress).isEqualTo(0)
        }
    }

    @Test
    fun getUserDailyQuest_existing_returnsExisting() {

        val user =
            userRepository.save(
                makeUser(
                    course1.id,
                    "testUser",
                    "test",
                    "user",
                    "testemail",
                    "pfp",
                    0,
                    FIXED_TIMESTAMP_1,
                    FIXED_TIMESTAMP_2,
                    0
                )
            )

        val userId = user.id!!

        userMonthlyChallengeRepository.save(
            makeUserMonthlyChallenge(
                userId,
                monthlyChallengeDefinition.id!!,
                FIXED_DATE_1,
                2
            )
        )

        for (i in questDefinitions.indices) {
            userDailyQuestRepository.save(
                makeUserDailyQuest(
                    userId,
                    questDefinitions[i].id!!,
                    FIXED_DATE_2,
                    1
                )
            )
        }

        val response = submitUserDailyQuestProgressRequest(userId)

        assertThat(response).isNotEmpty

        for (res in response) {
            assertThat(res.active).isEqualTo(true)
            assertThat(res.progress).isEqualTo(1)
        }
    }

    private fun submitUserDailyQuestProgressRequest(userId: Int): List<QuestResponse> {
        return given()
            .header("X-Test-User-Id", userId)
            .`when`()
            .get(pathConstants.QUESTS + pathConstants.GET_QUESTS_BY_USER)
            .then()
            .statusCode(200)
            .extract()
            .`as`(object : TypeRef<List<QuestResponse>>() {})
    }
}