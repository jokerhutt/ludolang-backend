package com.testingpractice.duoclonebackend.auth.app.service

import com.testingpractice.duoclonebackend.auth.app.service.AuthCookieService
import com.testingpractice.duoclonebackend.commons.exception.ApiException
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.Date

@Service
class JwtService(
    private val authCookieService: AuthCookieService
) {

    private val expirationMillis: Long = 1000 * 60 * 60 * 24 // 24h

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    private fun getSigningKey(): Key {
        return Keys.hmacShaKeyFor(jwtSecret.toByteArray(StandardCharsets.UTF_8))
    }

    fun createToken(userId: Int): String {
        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMillis))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun requireUserId(token: String?): Int {
        if (token.isNullOrBlank()) {
            throw ApiException(ErrorCode.MISSING_TOKEN)
        }

        try {
            val claims: Claims =
                Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .body

            return claims.subject.toInt()
        } catch (e: JwtException) {
            throw ApiException(ErrorCode.INVALID_TOKEN)
        }
    }
}