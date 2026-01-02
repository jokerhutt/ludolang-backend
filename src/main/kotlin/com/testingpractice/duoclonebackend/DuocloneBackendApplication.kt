package com.testingpractice.duoclonebackend;

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Clock

@SpringBootApplication
open class DuocloneBackendApplication

fun main(args: Array<String>) {
        SpringApplication.run(DuocloneBackendApplication::class.java, *args)
}

@Configuration
@Profile("!test")
open class ClockConfig {

  @Bean
  open fun clock(): Clock = Clock.systemDefaultZone()
}