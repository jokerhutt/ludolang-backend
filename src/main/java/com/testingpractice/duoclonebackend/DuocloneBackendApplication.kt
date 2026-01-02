package com.testingpractice.duoclonebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;

@SpringBootApplication
public class DuocloneBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(DuocloneBackendApplication.class, args);
  }
  @Configuration
  @Profile("!test")
  class ClockConfig {
    @Bean Clock clock() { return Clock.systemDefaultZone(); }
  }

}
