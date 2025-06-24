package com.example.soporteservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
@Bean
    OpenAPI apiInfo(){
        return new OpenAPI().info(new Info().title("Soporte API").version("1.0").description("Soporte de usuarios"));
    }
}

