package com.example.privileges_service.config;

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

      .title("Privilegios API")

      .version("2.0")

      .description("Privilegios de los usuario y administradores de FitLifeSPA"));

  }
}
