package com.example.login_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
        .info(new Info()
            .title("Login Service API")
            .version("1.0")
            .description("API para login de usuarios en FitLifeSPA"));
  }
}
