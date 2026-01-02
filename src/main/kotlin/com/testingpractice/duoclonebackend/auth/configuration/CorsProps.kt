package com.testingpractice.duoclonebackend.auth.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cors")
class CorsProps {

    var origins: List<String> = emptyList()
}