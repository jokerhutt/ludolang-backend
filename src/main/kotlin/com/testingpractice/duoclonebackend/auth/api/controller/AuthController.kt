package com.testingpractice.duoclonebackend.auth.api.controller

import com.testingpractice.duoclonebackend.auth.api.dto.TokenDto
import com.testingpractice.duoclonebackend.auth.app.service.AuthCookieService
import com.testingpractice.duoclonebackend.auth.app.service.GoogleService
import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.user.api.dto.UserResponse
import com.testingpractice.duoclonebackend.user.app.service.UserService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.AUTH)
class AuthController(
    private val googleService: GoogleService,
    private val authCookieService: AuthCookieService,
    private val userService: UserService
) {

    @PostMapping(pathConstants.GOOGLE_LOGIN)
    fun loginWithGoogle(
        @RequestBody dto: TokenDto,
        response: HttpServletResponse
    ): ResponseEntity<UserResponse> {
        val userDto =
            googleService.loginOrRegisterWithCode(dto.code, response)
        return ResponseEntity.ok(userDto)
    }

    @GetMapping(pathConstants.AUTH_ME)
    fun getCurrentUser(
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUser(userId))
    }

    @PostMapping(pathConstants.GOOGLE_LOGOUT)
    fun logout(response: HttpServletResponse): ResponseEntity<Void> {
        authCookieService.clearJwt(response)
        return ResponseEntity.ok().build()
    }
}