package com.testingpractice.duoclonebackend.controller

import com.testingpractice.duoclonebackend.commons.constants.pathConstants

import com.testingpractice.duoclonebackend.quest.api.dto.QuestResponse
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_DATE_2
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_DATE_2_1
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_1
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_2
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUser
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUserMonthlyChallenge

class MonthlyChallengeControllerIT : AbstractIntegrationTest() {

    @BeforeEach
    fun seed() {
    }

    @Test
    fun getUserMonthlyChallenge_noneExisting_createsAndReturnsNew() {

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

        val response = submitMonthlyProgressRequest(userId)

        assertThat(response).isNotNull
        assertThat(response.code).isNotNull
        assertThat(response.progress).isEqualTo(0)
        assertThat(response.total).isEqualTo(30)
        assertThat(response.active).isTrue
    }

    @Test
    fun getUserMonthlyChallenge_existing_returnsExisting() {

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
                FIXED_DATE_2,
                2
            )
        )

        val response = submitMonthlyProgressRequest(userId)

        assertThat(response).isNotNull
        assertThat(response.progress).isEqualTo(2)
        assertThat(response.total).isEqualTo(30)
        assertThat(response.active).isTrue
    }

    @Test
    fun getUserMonthlyChallenge_nextMonth_returnsNew() {

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

        val response = submitMonthlyProgressRequest(userId)

        assertThat(response).isNotNull
        assertThat(response.progress).isEqualTo(0)
        assertThat(response.total).isEqualTo(30)
        assertThat(response.active).isTrue
    }

    private fun submitMonthlyProgressRequest(userId: Int): QuestResponse {
        return given()
            .header("X-Test-User-Id", userId)
            .`when`()
            .get(pathConstants.MONTHLY_CHALLENGES + pathConstants.GET_MONTHLY_CHALLENGE_BY_USER)
            .then()
            .statusCode(200)
            .extract()
            .`as`(QuestResponse::class.java)
    }
}