package com.testingpractice.duoclonebackend.auth.infra.http

import com.testingpractice.duoclonebackend.auth.api.dto.GoogleTokenResponse
import com.testingpractice.duoclonebackend.auth.api.dto.GoogleUserInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Component
class GoogleOAuthClient {

    private val restTemplate = RestTemplate()

    @Value("\${google.client-id}")
    private lateinit var clientId: String

    @Value("\${google.client-secret}")
    private lateinit var clientSecret: String

    @Value("\${google.redirect-uri}")
    private lateinit var redirectUri: String

    fun exchangeCodeForAccessToken(code: String): GoogleTokenResponse {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("code", code)
        params.add("client_id", clientId)
        params.add("client_secret", clientSecret)
        params.add("redirect_uri", redirectUri)
        params.add("grant_type", "authorization_code")

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val request = HttpEntity(params, headers)

        return restTemplate.postForObject(
            "https://oauth2.googleapis.com/token",
            request,
            GoogleTokenResponse::class.java
        )!!
    }

    fun fetchUserInfo(accessToken: String): GoogleUserInfo {
        val url =
            "https://www.googleapis.com/oauth2/v3/userinfo?access_token=$accessToken"

        return restTemplate.getForObject(
            url,
            GoogleUserInfo::class.java
        )!!
    }
}