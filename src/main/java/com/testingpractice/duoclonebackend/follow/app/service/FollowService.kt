package com.testingpractice.duoclonebackend.follow.app.service

import com.testingpractice.duoclonebackend.exception.ApiException
import com.testingpractice.duoclonebackend.exception.ErrorCode
import com.testingpractice.duoclonebackend.follow.api.dto.FollowFollowingListResponse
import com.testingpractice.duoclonebackend.follow.api.dto.FollowResponse
import com.testingpractice.duoclonebackend.follow.domain.entity.Follow
import com.testingpractice.duoclonebackend.follow.infra.repository.FollowRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
open class FollowService (
    val followRepository: FollowRepository
) {

     fun getFollowersAndFollowingForUser(userId: Int): FollowFollowingListResponse {
        val followers = followRepository.findFollowerIdsByFollowedId(userId)
        val following = followRepository.findFollowedIdsByFollowerId(userId)

        return FollowFollowingListResponse(following, followers)
    }

    @Transactional
    open fun handleFollow(followerId: Int, followedId: Int): FollowResponse {
        val alreadyFollows =
            followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)
        if (alreadyFollows) throw ApiException(ErrorCode.ALREADY_FOLLOWS)

        val newFollow =
            Follow(followerId = followerId, followedId = followedId, createdAt = Timestamp.from(Instant.now()))

        followRepository.save(newFollow)

        return getNewStatsForParties(followerId, followedId)
    }

    @Transactional
    open fun handleUnfollow(followerId: Int, followedId: Int): FollowResponse {
        val alreadyFollows =
            followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)
        if (!alreadyFollows) throw ApiException(ErrorCode.DOES_NOT_FOLLOW)

        val toDelete =
            followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
        followRepository.delete(toDelete)

        return getNewStatsForParties(followerId, followedId)
    }

    private fun getNewStatsForParties(
        followerId: Int,
        followedId: Int
    ): FollowResponse {
        val followerNewStats = getFollowersAndFollowingForUser(followerId)
        val followedNewStats = getFollowersAndFollowingForUser(followedId)

        return FollowResponse(
            followerId,
            followedId,
            followerNewStats,
            followedNewStats
        )
    }
}