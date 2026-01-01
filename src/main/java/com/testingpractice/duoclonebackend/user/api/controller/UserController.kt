package com.testingpractice.duoclonebackend.user.api.controller
import com.testingpractice.duoclonebackend.constants.pathConstants
import com.testingpractice.duoclonebackend.dto.UserCourseProgressDto
import com.testingpractice.duoclonebackend.service.UserCreationService
import com.testingpractice.duoclonebackend.user.api.dto.UpdateAvatarRequest
import com.testingpractice.duoclonebackend.user.api.dto.UserResponse
import com.testingpractice.duoclonebackend.user.app.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.USERS)
class UserController(
    private val userService: UserService,
    private val userCreationService: UserCreationService
) {

    @GetMapping(pathConstants.GET_USER_COURSE_PROGRESS)
    fun getUserCourseProgress(
        @PathVariable("courseId") courseId: Int,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): UserCourseProgressDto {
        return userService.getUserCourseProgress(courseId, userId)
    }

    @GetMapping(pathConstants.GET_USER_BY_ID)
    fun getUserById(
        @PathVariable userId: Int
    ): UserResponse {
        return userService.getUser(userId)
    }

    @GetMapping(pathConstants.GET_USERS_FROM_IDS)
    fun getUsersByIds(
        @RequestParam userIds: List<Int>
    ): List<UserResponse> {
        return userService.getUsersFromIds(userIds)
    }

    @GetMapping(pathConstants.GET_AVATARS)
    fun getAvatars(): List<String> {
        return userCreationService.getDefaultProfilePics()
    }

    @PostMapping(pathConstants.UPDATE_AVATAR)
    fun updateAvatar(
        @RequestBody updateAvatarRequest: UpdateAvatarRequest,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): UserResponse {
        return userCreationService.updateAvatar(
            userId,
            updateAvatarRequest.selectedAvatar
        )
    }
}