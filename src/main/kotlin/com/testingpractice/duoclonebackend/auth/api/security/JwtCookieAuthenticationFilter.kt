package com.testingpractice.duoclonebackend.auth.api.security

import com.testingpractice.duoclonebackend.auth.api.dto.AuthUser
import com.testingpractice.duoclonebackend.auth.app.service.AuthCookieService
import com.testingpractice.duoclonebackend.auth.app.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ResponseStatusException
import java.io.IOException

@Component
class JwtCookieAuthenticationFilter(
    private val authCookieService: AuthCookieService,
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {

        val token = authCookieService.readJwt(req)
        if (token != null) {
            try {
                val userId = jwtService.requireUserId(token)
                val principal = AuthUser(userId)
                val auth =
                    UsernamePasswordAuthenticationToken(principal, null, emptyList())

                auth.details =
                    WebAuthenticationDetailsSource().buildDetails(req)

                SecurityContextHolder.getContext().authentication = auth
            } catch (e: Exception) {
                println("JWT invalid: ${e.message}")
            }
        }

        chain.doFilter(req, res)
    }
}