package com.testingpractice.duoclonebackend.follow.api.controller

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.follow.app.service.FollowService
import com.testingpractice.duoclonebackend.follow.api.dto.FollowFollowingListResponse
import com.testingpractice.duoclonebackend.follow.api.dto.FollowRequest
import com.testingpractice.duoclonebackend.follow.api.dto.FollowResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.FOLLOWS)
@RequiredArgsConstructor
class FollowController(private val followService: FollowService) {

    @GetMapping(pathConstants.GET_FOLLOWS_BY_USER)
    fun getFollowersAndFollowingForUser(@PathVariable userId: Int) : FollowFollowingListResponse {
        return followService.getFollowersAndFollowingForUser(userId)
    }

    @PostMapping(pathConstants.FOLLOW_USER)
    fun followUser (@RequestBody followRequest: FollowRequest, @AuthenticationPrincipal(expression = "id") followerId: Int) : FollowResponse {
        return followService.handleFollow(followerId, followRequest.followedId)
    }

    @PostMapping(pathConstants.UNFOLLOW_USER)
    fun unfollowUser (@RequestBody followRequest: FollowRequest, @AuthenticationPrincipal(expression = "id") followerId: Int) : FollowResponse {
        return followService.handleUnfollow(followerId, followRequest.followedId)
    }



}