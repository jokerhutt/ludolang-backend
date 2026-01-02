package com.testingpractice.duoclonebackend.controller;

import com.testingpractice.duoclonebackend.commons.constants.pathConstants;
import com.testingpractice.duoclonebackend.dto.QuestResponse;
import com.testingpractice.duoclonebackend.entity.User;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.testingpractice.duoclonebackend.testutils.TestConstants.*;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestControllerIT extends AbstractIntegrationTest{

    @BeforeEach
    void seed () {

    }

    @Test
    void getUserDailyQuest_noneExisting_createsAndReturnsNew () {

        User user = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer userId = user.getId();

        userMonthlyChallengeRepository.save(makeUserMonthlyChallenge(userId, monthlyChallengeDefinition.getId(), FIXED_DATE_2_1, 2));

        List<QuestResponse> response = submitUserDailyQuestProgressRequest(userId);

        assertThat(response).isNotEmpty();

        for (QuestResponse res: response) {
            assertThat(res.active()).isEqualTo(true);
            assertThat(res.progress()).isEqualTo(0);
        }

    }

    @Test
    void getUserDailyQuest_nextDay_returnsNew () {

        User user = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer userId = user.getId();

        userMonthlyChallengeRepository.save(makeUserMonthlyChallenge(userId, monthlyChallengeDefinition.getId(), FIXED_DATE_1, 2));

        for (int i = 0; i < questDefinitions.size(); i++) {
            userDailyQuestRepository.save(makeUserDailyQuest(userId, questDefinitions.get(i).getId(), FIXED_DATE_1, 1));
        }

        List<QuestResponse> response = submitUserDailyQuestProgressRequest(userId);

        assertThat(response).isNotEmpty();

        for (QuestResponse res: response) {
            assertThat(res.active()).isEqualTo(true);
            assertThat(res.progress()).isEqualTo(0);
        }

    }

    @Test
    void getUserDailyQuest_existing_returnsExisting () {
        User user = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer userId = user.getId();

        userMonthlyChallengeRepository.save(makeUserMonthlyChallenge(userId, monthlyChallengeDefinition.getId(), FIXED_DATE_1, 2));

        for (int i = 0; i < questDefinitions.size(); i++) {
            userDailyQuestRepository.save(makeUserDailyQuest(userId, questDefinitions.get(i).getId(), FIXED_DATE_2, 1));
        }

        List<QuestResponse> response = submitUserDailyQuestProgressRequest(userId);

        assertThat(response).isNotEmpty();

        for (QuestResponse res: response) {
            assertThat(res.active()).isEqualTo(true);
            assertThat(res.progress()).isEqualTo(1);
        }

    }


    private List<QuestResponse> submitUserDailyQuestProgressRequest(Integer userId) {
        return given()
                .header("X-Test-User-Id", userId)
                .when()
                .get(pathConstants.QUESTS + pathConstants.GET_QUESTS_BY_USER)
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<QuestResponse>>() {});
    }

}
