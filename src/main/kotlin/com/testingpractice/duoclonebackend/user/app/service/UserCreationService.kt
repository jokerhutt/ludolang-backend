package com.testingpractice.duoclonebackend.user.app.service

import com.testingpractice.duoclonebackend.auth.api.dto.GoogleUserInfo
import com.testingpractice.duoclonebackend.catalog.app.service.LookupService
import com.testingpractice.duoclonebackend.quest.app.service.MonthlyChallengeService
import com.testingpractice.duoclonebackend.quest.app.service.QuestService
import com.testingpractice.duoclonebackend.user.api.dto.UserResponse
import com.testingpractice.duoclonebackend.user.app.mapper.UserMapper
import com.testingpractice.duoclonebackend.user.app.util.UserCreationUtils
import com.testingpractice.duoclonebackend.user.domain.entity.User
import com.testingpractice.duoclonebackend.user.infra.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
open class UserCreationService(
    private val userRepository: UserRepository,
    private val questService: QuestService,
    private val monthlyChallengeService: MonthlyChallengeService,
    private val lookupService: LookupService,
    private val userMapper: UserMapper
) {

    @Transactional
    open fun createUser(googleUser: GoogleUserInfo): User {
        val newUser = User(
            id = null,
            email = googleUser.email,
            firstName = googleUser.givenName!!,
            lastName = googleUser.familyName!!,
            username = UserCreationUtils.generateUsername(googleUser.name),
            currentCourseId = null,
            pfpSrc = UserCreationUtils.getRandomProfilePic(),
            points = 0,
            streakLength = 0,
            createdAt = Timestamp.from(Instant.now())
        )

        userRepository.save(newUser)

        // generate daily quests + monthly challenge
        questService.getQuestsForUser(newUser.id!!)
        monthlyChallengeService.getMonthlyChallengeForUser(newUser.id!!)

        return newUser
    }

    @Transactional
    open fun updateAvatar(userId: Int, newAvatarSrc: String): UserResponse {
        val user = lookupService.userOrThrow(userId)
        val updatedUser = user.copy(pfpSrc = newAvatarSrc)

        userRepository.save(updatedUser)
        return userMapper.toUserResponse(updatedUser)
    }

    fun getDefaultProfilePics(): List<String> =
        UserCreationUtils.getAllProfilePics()
}