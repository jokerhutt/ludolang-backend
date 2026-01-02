package com.testingpractice.duoclonebackend.controller;

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import com.testingpractice.duoclonebackend.follow.api.dto.FollowFollowingListResponse
import com.testingpractice.duoclonebackend.follow.api.dto.FollowResponse
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_1
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_2
import com.testingpractice.duoclonebackend.testutils.TestConstants.FIXED_TIMESTAMP_2_2
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import io.restassured.RestAssured.given
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.hamcrest.Matchers.equalTo

import com.testingpractice.duoclonebackend.testutils.TestUtils.makeFollow
import com.testingpractice.duoclonebackend.testutils.TestUtils.makeUser

open class FollowControllerIT : AbstractIntegrationTest() {

    @BeforeEach
    fun seed() {
    }

    private val followPath = pathConstants.FOLLOWS + pathConstants.FOLLOW_USER
    private val unfollowPath = pathConstants.FOLLOWS + pathConstants.UNFOLLOW_USER

    @Test
    fun followUser_noFollowExists_createsFollow() {

        val follower =
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
        val followerId = follower.id

        val followed =
                userRepository.save(
                        makeUser(
                                course1.id,
                                "testUser2",
                                "test2",
                                "user2",
                                "testemail2",
                                "pfp",
                                0,
                                FIXED_TIMESTAMP_1,
                                FIXED_TIMESTAMP_2,
                                0
                        )
                )
        val followedId = followed.id

        val response = submitFollowPostBody(followerId!!, followedId!!, followPath)

        assertThat(response).isNotNull
        assertThat(response.followedNewStats.followerIds.size).isEqualTo(1)
        assertThat(response.followedNewStats.followingIds.size).isEqualTo(0)
        assertThat(response.followersNewStats.followingIds.size).isEqualTo(1)
        assertThat(response.followersNewStats.followerIds.size).isEqualTo(0)
    }

    @Test
    fun unfollowUser_followExists_unfollowsUser() {

        val follower =
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
        val followerId = follower.id

        val followed =
                userRepository.save(
                        makeUser(
                                course1.id,
                                "testUser2",
                                "test2",
                                "user2",
                                "testemail2",
                                "pfp",
                                0,
                                FIXED_TIMESTAMP_1,
                                FIXED_TIMESTAMP_2,
                                0
                        )
                )
        val followedId = followed.id

        followRepository.save(makeFollow(followerId!!, followedId!!, FIXED_TIMESTAMP_1))

        val response = submitFollowPostBody(followerId, followedId, unfollowPath)

        assertThat(response).isNotNull
        println(response.followedNewStats.followerIds)
        println(response.followedNewStats.followingIds)
        assertThat(response.followedNewStats.followerIds.size).isEqualTo(0)
        assertThat(response.followedNewStats.followingIds.size).isEqualTo(0)
        assertThat(response.followersNewStats.followingIds.size).isEqualTo(0)
        assertThat(response.followersNewStats.followerIds.size).isEqualTo(0)
    }

    @Test
    fun followUser_followExists_doesNotUnfollow_throwsError() {

        val follower =
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
        val followerId = follower.id

        val followed =
                userRepository.save(
                        makeUser(
                                course1.id,
                                "testUser2",
                                "test2",
                                "user2",
                                "testemail2",
                                "pfp",
                                0,
                                FIXED_TIMESTAMP_1,
                                FIXED_TIMESTAMP_2,
                                0
                        )
                )
        val followedId = followed.id

        followRepository.save(makeFollow(followerId!!, followedId!!, FIXED_TIMESTAMP_1))

        given()
                .header("X-Test-User-Id", followerId)
                .contentType("application/json")
                .body(mapOf("followedId" to followedId))
                .`when`()
                .post(followPath)
                .then()
                .statusCode(ErrorCode.ALREADY_FOLLOWS.status().value())
                .body("title", equalTo(ErrorCode.ALREADY_FOLLOWS.name))
                .body("detail", equalTo(ErrorCode.ALREADY_FOLLOWS.defaultMessage()))
                .body("status", equalTo(ErrorCode.ALREADY_FOLLOWS.status().value()))
    }

    @Test
    fun unfollowUser_noFollowExists_doesNotUnfollow_throwsError() {

        val follower =
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
        val followerId = follower.id

        val followed =
                userRepository.save(
                        makeUser(
                                course1.id,
                                "testUser2",
                                "test2",
                                "user2",
                                "testemail2",
                                "pfp",
                                0,
                                FIXED_TIMESTAMP_1,
                                FIXED_TIMESTAMP_2,
                                0
                        )
                )
        val followedId = followed.id

        given()
                .header("X-Test-User-Id", followerId)
                .contentType("application/json")
                .body(mapOf("followedId" to followedId))
                .`when`()
                .post(unfollowPath)
                .then()
                .statusCode(ErrorCode.DOES_NOT_FOLLOW.status().value())
                .body("title", equalTo(ErrorCode.DOES_NOT_FOLLOW.name))
                .body("detail", equalTo(ErrorCode.DOES_NOT_FOLLOW.defaultMessage()))
                .body("status", equalTo(ErrorCode.DOES_NOT_FOLLOW.status().value()))
    }

    @Test
    fun getFollowersAndFollowingForUser_noFollows_returnsEmptyLists() {

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

        val dummyUser1 =
                userRepository.save(makeUser(course1.id, "testUser1", "test2", "user2", "testemail2", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0))
        val dummyUser2 =
                userRepository.save(makeUser(course1.id, "testUser2", "test3", "user3", "testemail3", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0))
        val dummyUser3 =
                userRepository.save(makeUser(course1.id, "testUser3", "test4", "user4", "testemail4", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0))

        followRepository.saveAll(
                listOf(
                        makeFollow(dummyUser1.id!!, dummyUser2.id!!, FIXED_TIMESTAMP_1),
                        makeFollow(dummyUser2.id!!, dummyUser3.id!!, FIXED_TIMESTAMP_2_2),
                        makeFollow(dummyUser3.id!!, dummyUser1.id!!, FIXED_TIMESTAMP_2)
                )
        )

        val response = submitFollowRequestBody(userId!!)

        assertThat(response).isNotNull
        assertThat(response.followerIds.size).isEqualTo(0)
        assertThat(response.followingIds.size).isEqualTo(0)
    }

    @Test
    fun getFollowersAndFollowingForUser_followsExist_returnsLists() {

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

        val dummyUser1 =
                userRepository.save(makeUser(course1.id, "testUser1", "test2", "user2", "testemail2", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0))
        val dummyUser2 =
                userRepository.save(makeUser(course1.id, "testUser2", "test3", "user3", "testemail3", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0))
        val dummyUser3 =
                userRepository.save(makeUser(course1.id, "testUser3", "test4", "user4", "testemail4", "pfp", 0, FIXED_TIMESTAMP_1, FIXED_TIMESTAMP_2, 0))

        followRepository.saveAll(
                listOf(
                        makeFollow(dummyUser1.id!!, userId!!, FIXED_TIMESTAMP_1),
                        makeFollow(dummyUser2.id!!, userId!!, FIXED_TIMESTAMP_2),
                        makeFollow(userId!!, dummyUser3.id!!, FIXED_TIMESTAMP_2_2)
                )
        )

        val response = submitFollowRequestBody(userId)

        assertThat(response).isNotNull
        println(response.followerIds.size)
        assertThat(response.followerIds.size).isEqualTo(2)
        assertThat(response.followingIds.size).isEqualTo(1)
    }

    private fun submitFollowRequestBody(userId: Int): FollowFollowingListResponse {
        return given()
                .contentType("application/json")
                .`when`()
                .get(
                        pathConstants.FOLLOWS +
                                pathConstants.GET_FOLLOWS_BY_USER.replace("{userId}", userId.toString())
                )
                .then()
                .statusCode(200)
                .extract()
                .`as`(FollowFollowingListResponse::class.java)
    }

    private fun submitFollowPostBody(
            userId: Int,
            followedId: Int,
            path: String
    ): FollowResponse {
        return given()
                .header("X-Test-User-Id", userId)
                .contentType("application/json")
                .body(mapOf("followedId" to followedId))
                .`when`()
                .post(path)
                .then()
                .statusCode(200)
                .extract()
                .`as`(FollowResponse::class.java)
    }
}