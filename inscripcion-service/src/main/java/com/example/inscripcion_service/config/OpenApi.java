package com.example.inscripcion_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApi {
    @Bean

  public OpenAPI apiInfo(){

    return new OpenAPI()

    .info(new Info()

      .title("Inscripcion Servicios API")

      .version("1.0")

      .description("Inscripciones de los Servicios de FitLifeSPA"));

  }
}
