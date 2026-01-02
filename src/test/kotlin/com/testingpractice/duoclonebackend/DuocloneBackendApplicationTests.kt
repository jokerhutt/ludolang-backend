package com.testingpractice.duoclonebackend;

import com.testingpractice.duoclonebackend.controller.AbstractIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DuocloneBackendApplicationTests : AbstractIntegrationTest() {

  @Test
  fun contextLoads() {
  }
}