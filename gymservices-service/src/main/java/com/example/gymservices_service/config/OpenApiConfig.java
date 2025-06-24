package com.example.gymservices_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
     @Bean
  public OpenAPI gymServicesApiInfo() {
    return new OpenAPI()
        .info(new Info()
            .title("Gym Services API")
            .version("1.0")
            .description("Gestión de servicios ofrecidos por el gimnasio FitLifeSPA"));
  }

}
