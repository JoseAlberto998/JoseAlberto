
package com.mycompany.clinicaveterinariajosealberto.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI myOpenAPI() {

    Info info = new Info()
        .title("Clinica veterinaria Jose Alberto")
        .version("1.0")
        .description("Rest api clinica veterinaria.");

    return new OpenAPI().info(info);
  }
}

