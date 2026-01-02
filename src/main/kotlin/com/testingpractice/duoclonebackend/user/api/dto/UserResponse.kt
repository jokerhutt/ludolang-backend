package com.testingpractice.duoclonebackend.user.api.dto

import java.sql.Timestamp

data class UserResponse(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val currentCourseId: Int?,
    val pfpSrc: String?,
    val points: Int,
    val streakLength: Int,
    val createdAt: Timestamp
)