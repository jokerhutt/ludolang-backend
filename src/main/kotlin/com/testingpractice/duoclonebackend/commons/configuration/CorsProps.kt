package com.testingpractice.duoclonebackend.commons.configuration
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cors")
data class CorsProps(
    var origins: List<String> = listOf()
)