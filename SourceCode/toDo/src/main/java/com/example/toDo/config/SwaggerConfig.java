package com.example.toDo.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

import static java.awt.SystemColor.info;

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
        .info(new Info()
                .title("ToDo API")
                .version("1.0")
                .description("API documentation for ToDo application"));
    }
}
