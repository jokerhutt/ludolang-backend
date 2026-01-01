package com.testingpractice.duoclonebackend.user.app.mapper

import com.testingpractice.duoclonebackend.commons.BasicMapper
import com.testingpractice.duoclonebackend.user.api.dto.UserResponse
import com.testingpractice.duoclonebackend.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val basicMapper: BasicMapper
) {

    fun toUserResponse(user: User): UserResponse =
        basicMapper.one(user) {
            UserResponse(
                id = it.id!!,
                username = it.username!!,
                firstName = it.firstName!!,
                lastName = it.lastName!!,
                currentCourseId = it.currentCourseId,
                pfpSrc = it.pfpSrc,
                points = it.points!!,
                streakLength = it.streakLength!!,
                createdAt = it.createdAt!!
            )
        }

    fun toUserResponseList(users: List<User>): List<UserResponse> =
        basicMapper.list(users) { user ->
            toUserResponse(user)
        }
}