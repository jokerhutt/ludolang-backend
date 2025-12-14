package com.testingpractice.duoclonebackend.security;

import com.testingpractice.duoclonebackend.auth.CorsProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(CorsProps.class)
public class WebConfig implements WebMvcConfigurer {

  private final CorsProps corsProps;

  public WebConfig(CorsProps corsProps) {
    this.corsProps = corsProps;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins(
                    "http://localhost:5173",
                    "https://duoclone.jokerhut.com",
                    "https://www.duoclone.jokerhut.com",
                    "https://exquisite-lily-dbf9cd.netlify.app"
            )
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
  }
}