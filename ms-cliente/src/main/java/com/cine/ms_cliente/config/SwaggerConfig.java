package com.cine.ms_cliente.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Clientes - Sistema de Cine")
                        .version("1.0")
                        .description("Documentación oficial del microservicio encargado de gestionar los clientes y sus tickets."));
    }
}