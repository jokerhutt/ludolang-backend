package com.testingpractice.duoclonebackend.auth.app.service

import com.testingpractice.duoclonebackend.auth.app.service.AuthCookieService
import com.testingpractice.duoclonebackend.service.JwtService
import com.testingpractice.duoclonebackend.user.api.dto.UserResponse
import com.testingpractice.duoclonebackend.user.app.mapper.UserMapper
import com.testingpractice.duoclonebackend.user.app.service.UserCreationService
import com.testingpractice.duoclonebackend.user.infra.repository.UserRepository
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
open class GoogleService(
    private val oauth: GoogleOAuthClient,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val jwtService: JwtService,
    private val userCreationService: UserCreationService,
    private val authCookieService: AuthCookieService
) {

    companion object {
        private const val DAY = 24 * 60 * 60
    }

    @Transactional
    open fun loginOrRegisterWithCode(
        code: String,
        response: HttpServletResponse
    ): UserResponse {

        // 1) Exchange code → access token
        val token = oauth.exchangeCodeForAccessToken(code)

        // 2) Fetch user info
        val googleUser = oauth.fetchUserInfo(token.accessToken())

        // 3) Find user or create
        val user =
            userRepository.findByEmail(googleUser.email!!)
                .orElseGet { userCreationService.createUser(googleUser) }

        // 4) Create JWT
        val jwt = jwtService.createToken(user.id!!)

        // 5) Set cookie
        authCookieService.setJwt(response, jwt, DAY)

        return userMapper.toUserResponse(user)
    }
}