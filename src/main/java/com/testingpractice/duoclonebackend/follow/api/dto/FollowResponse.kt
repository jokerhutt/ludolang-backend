package com.testingpractice.duoclonebackend.follow.api.dto

data class FollowResponse(val actorUserId: Int, val followedUserId: Int, val followersNewStats: FollowFollowingListResponse, val followedNewStats: FollowFollowingListResponse)
