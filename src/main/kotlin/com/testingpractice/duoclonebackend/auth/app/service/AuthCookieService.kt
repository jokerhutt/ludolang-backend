package com.testingpractice.duoclonebackend.auth.app.service

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component

@Component
class AuthCookieService {

    @Value("\${auth.cookie.name:jwt}")
    private lateinit var cookieName: String

    @Value("\${auth.cookie.path:/}")
    private lateinit var cookiePath: String

    @Value("\${auth.cookie.domain:localhost}")
    private lateinit var cookieDomain: String

    @Value("\${auth.cookie.same-site:Lax}")
    private lateinit var sameSite: String

    @Value("\${auth.cookie.secure:false}")
    private var secure: Boolean = true

    @Value("\${auth.cookie.max-age-seconds:86400}")
    private var defaultMaxAge: Long = 0

    fun setJwt(response: HttpServletResponse, jwt: String) {
        addCookie(response, jwt, defaultMaxAge)
    }

    fun setJwt(response: HttpServletResponse, jwt: String, maxAgeSeconds: Long) {
        addCookie(response, jwt, maxAgeSeconds)
    }

    fun clearJwt(response: HttpServletResponse) {
        addCookie(response, "", 0)
    }

    fun readJwt(request: HttpServletRequest): String? {
        val cookies: Array<Cookie>? = request.cookies ?: return null
        if (cookies != null) {
            for (c in cookies) {
                if (cookieName == c.name) return c.value
            }
        }
        return null
    }

    private fun addCookie(
        response: HttpServletResponse,
        value: String,
        maxAgeSeconds: Long
    ) {
        val cookie =
            ResponseCookie.from(cookieName, value)
                .httpOnly(true)
                .secure(secure)
                .sameSite(sameSite)
                .path(cookiePath)
                .maxAge(maxAgeSeconds)
                .domain(if (cookieDomain.isBlank()) null else cookieDomain)
                .build()

        response.addHeader("Set-Cookie", cookie.toString())
    }
}