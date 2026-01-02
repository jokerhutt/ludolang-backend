package com.testingpractice.duoclonebackend.controller;

import com.testingpractice.duoclonebackend.commons.constants.pathConstants;
import com.testingpractice.duoclonebackend.entity.User;
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode;
import com.testingpractice.duoclonebackend.follow.api.dto.FollowFollowingListResponse;
import com.testingpractice.duoclonebackend.follow.api.dto.FollowResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static com.testingpractice.duoclonebackend.testutils.TestConstants.*;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.makeFollow;
import static com.testingpractice.duoclonebackend.testutils.TestUtils.makeUser;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FollowControllerIT extends AbstractIntegrationTest{

    @BeforeEach
    void seed () {

    }

    private String followPath = pathConstants.FOLLOWS + pathConstants.FOLLOW_USER;
    private String unfollowPath = pathConstants.FOLLOWS + pathConstants.UNFOLLOW_USER;

    @Test
    void followUser_noFollowExists_createsFollow () {

        User follower = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer followerId = follower.getId();

        User followed = userRepository.save(makeUser(course1.getId(), "testUser2", "test2", "user2", "testemail2", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer followedId = followed.getId();

        FollowResponse response = submitFollowPostBody(followerId, followedId, followPath);

        assertThat(response).isNotNull();
        assertThat(response.getFollowedNewStats().getFollowerIds().size()).isEqualTo(1);
        assertThat(response.getFollowedNewStats().getFollowingIds().size()).isEqualTo(0);
        assertThat(response.getFollowersNewStats().getFollowingIds().size()).isEqualTo(1);
        assertThat(response.getFollowersNewStats().getFollowerIds().size()).isEqualTo(0);

    }

    @Test
    void unfollowUser_followExists_unfollowsUser () {

        User follower = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer followerId = follower.getId();

        User followed = userRepository.save(makeUser(course1.getId(), "testUser2", "test2", "user2", "testemail2", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer followedId = followed.getId();

        followRepository.save(makeFollow(followerId, followedId, FIXED_TIMESTAMP_1));

        FollowResponse response = submitFollowPostBody(followerId, followedId, unfollowPath);

        assertThat(response).isNotNull();
        assertThat(response.getFollowedNewStats().getFollowerIds().size()).isEqualTo(0);
        assertThat(response.getFollowedNewStats().getFollowingIds().size()).isEqualTo(0);
        assertThat(response.getFollowersNewStats().getFollowingIds().size()).isEqualTo(0);
        assertThat(response.getFollowersNewStats().getFollowerIds().size()).isEqualTo(0);

    }

    @Test
    void followUser_followExists_doesNotUnfollow_throwsError () {

        User follower = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer followerId = follower.getId();

        User followed = userRepository.save(makeUser(course1.getId(), "testUser2", "test2", "user2", "testemail2", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer followedId = followed.getId();

        followRepository.save(makeFollow(followerId, followedId, FIXED_TIMESTAMP_1));

        given()
                .header("X-Test-User-Id", followerId)
                .contentType("application/json")
                .body(Map.of("followedId", followedId))
                .when()
                .post(followPath)
                .then()
                .statusCode(ErrorCode.ALREADY_FOLLOWS.status().value())
                .body("title", equalTo(ErrorCode.ALREADY_FOLLOWS.name()))
                .body("detail", equalTo(ErrorCode.ALREADY_FOLLOWS.defaultMessage()))
                .body("status", equalTo(ErrorCode.ALREADY_FOLLOWS.status().value()));

    }

    @Test
    void unfollowUser_noFollowExists_doesNotUnfollow_throwsError () {

        User follower = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer followerId = follower.getId();

        User followed = userRepository.save(makeUser(course1.getId(), "testUser2", "test2", "user2", "testemail2", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer followedId = followed.getId();

        given()
                .header("X-Test-User-Id", followerId)
                .contentType("application/json")
                .body(Map.of("followedId", followedId))
                .when()
                .post(unfollowPath)
                .then()
                .statusCode(ErrorCode.DOES_NOT_FOLLOW.status().value())
                .body("title", equalTo(ErrorCode.DOES_NOT_FOLLOW.name()))
                .body("detail", equalTo(ErrorCode.DOES_NOT_FOLLOW.defaultMessage()))
                .body("status", equalTo(ErrorCode.DOES_NOT_FOLLOW.status().value()));

    }

    @Test
    void getFollowersAndFollowingForUser_noFollows_returnsEmptyLists () {

        User user = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer userId = user.getId();

        User dummyUser1 =  userRepository.save(makeUser(course1.getId(), "testUser1", "test2", "user2", "testemail2", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        User dummyUser2 =  userRepository.save(makeUser(course1.getId(), "testUser2", "test3", "user3", "testemail3", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        User dummyUser3 =  userRepository.save(makeUser(course1.getId(), "testUser3", "test4", "user4", "testemail4", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));

        followRepository.saveAll(List.of(
                makeFollow(dummyUser1.getId(), dummyUser2.getId(), FIXED_TIMESTAMP_1),
                makeFollow(dummyUser2.getId(), dummyUser3.getId(), FIXED_TIMESTAMP_2_2),
                makeFollow(dummyUser3.getId(), dummyUser1.getId(), FIXED_TIMESTAMP_2)
        ));


        FollowFollowingListResponse response = submitFollowRequestBody(userId);

        assertThat(response).isNotNull();
        assertThat(response.getFollowerIds().size()).isEqualTo(0);
        assertThat(response.getFollowingIds().size()).isEqualTo(0);

    }

    @Test
    void getFollowersAndFollowingForUser_followsExist_returnsLists () {

        User user = userRepository.save(makeUser(course1.getId(), "testUser", "test", "user", "testemail", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        Integer userId = user.getId();

        User dummyUser1 =  userRepository.save(makeUser(course1.getId(), "testUser1", "test2", "user2", "testemail2", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        User dummyUser2 =  userRepository.save(makeUser(course1.getId(), "testUser2", "test3", "user3", "testemail3", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));
        User dummyUser3 =  userRepository.save(makeUser(course1.getId(), "testUser3", "test4", "user4", "testemail4", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0));

        followRepository.saveAll(List.of(
                makeFollow(dummyUser1.getId(), userId, FIXED_TIMESTAMP_1),
                makeFollow(dummyUser2.getId(), userId, FIXED_TIMESTAMP_2),
                makeFollow(userId, dummyUser3.getId(), FIXED_TIMESTAMP_2_2)
        ));

        FollowFollowingListResponse response = submitFollowRequestBody(userId);
        assertThat(response).isNotNull();
        assertThat(response.getFollowerIds().size()).isEqualTo(2);
        assertThat(response.getFollowingIds().size()).isEqualTo(1);

    }


    private FollowFollowingListResponse submitFollowRequestBody (Integer userId) {
        return
                given()
                        .contentType("application/json")
                        .when()
                        .get(pathConstants.FOLLOWS + pathConstants.GET_FOLLOWS_BY_USER.replace("{userId}", userId.toString()))
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(FollowFollowingListResponse.class);
    }



    private FollowResponse submitFollowPostBody(Integer userId, Integer followedId, String path) {
        return
                given()
                        .header("X-Test-User-Id", userId)
                        .contentType("application/json")
                        .body(
                                Map.of(
                                        "followedId", followedId
                                )
                        )
                        .when()
                        .post(path)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(FollowResponse.class);
    }





}
