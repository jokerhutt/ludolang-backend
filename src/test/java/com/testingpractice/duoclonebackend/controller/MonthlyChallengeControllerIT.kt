package com.testingpractice.duoclonebackend.controller;

import com.testingpractice.duoclonebackend.commons.constants.pathConstants;
import com.testingpractice.duoclonebackend.dto.QuestResponse;
import com.testingpractice.duoclonebackend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.testingpractice.duoclonebackend.testutils.TestConstants.*;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.makeUser;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.makeUserMonthlyChallenge;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class MonthlyChallengeControllerIT extends AbstractIntegrationTest{

    @BeforeEach
    void seed () {

    }

    @Test
    void getUserMonthlyChallenge_noneExisting_createsAndReturnsNew () {

        User user = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer userId = user.getId();

        QuestResponse response = submitMonthlyProgressRequest(userId);

        assertThat(response).isNotNull();
        assertThat(response.code()).isNotNull();
        assertThat(response.progress()).isEqualTo(0);
        assertThat(response.total()).isEqualTo(30);
        assertThat(response.active()).isTrue();

    }

    @Test
    void getUserMonthlyChallenge_existing_returnsExisting () {

        User user = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer userId = user.getId();

        userMonthlyChallengeRepository.save(makeUserMonthlyChallenge(userId, monthlyChallengeDefinition.getId(), FIXED_DATE_2, 2));
        QuestResponse response = submitMonthlyProgressRequest(userId);

        assertThat(response).isNotNull();
        assertThat(response.progress()).isEqualTo(2);
        assertThat(response.total()).isEqualTo(30);
        assertThat(response.active()).isTrue();

    }

    @Test
    void getUserMonthlyChallenge_nextMonth_returnsNew () {

        User user = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer userId = user.getId();

        userMonthlyChallengeRepository.save(makeUserMonthlyChallenge(userId, monthlyChallengeDefinition.getId(), FIXED_DATE_2_1, 2));

        QuestResponse response = submitMonthlyProgressRequest(userId);
        assertThat(response).isNotNull();
        assertThat(response.progress()).isEqualTo(0);
        assertThat(response.total()).isEqualTo(30);
        assertThat(response.active()).isTrue();

    }

    private QuestResponse submitMonthlyProgressRequest(Integer userId) {
        return given()
                .header("X-Test-User-Id", userId)
                .when()
                .get(pathConstants.MONTHLY_CHALLENGES + pathConstants.GET_MONTHLY_CHALLENGE_BY_USER)
                .then()
                .statusCode(200)
                .extract()
                .as(QuestResponse.class);
    }





}
