package com.example.reserva_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApi {
    @Bean
    OpenAPI apiInfo(){

    return new OpenAPI()

    .info(new Info()

      .title("Reserva Servicios API")

      .version("1.0")

      .description("Reserva de Servicios de FitLifeSPA"));

  }
}
