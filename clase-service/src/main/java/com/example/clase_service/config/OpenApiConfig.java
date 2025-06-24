package com.example.clase_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
  public OpenAPI claseApiInfo() {
    return new OpenAPI()
        .info(new Info()
            .title("Clase Service API")
            .version("1.0")
            .description("Gestión de clases en FitLifeSPA"));
  }
}
