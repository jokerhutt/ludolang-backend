package com.testingpractice.duoclonebackend.leaderboard.api.dto

import com.testingpractice.duoclonebackend.user.api.dto.UserResponse

data class LeaderboardPageDto(val users: List<UserResponse>, val nextCursor: String?)
