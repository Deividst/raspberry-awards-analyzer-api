package com.github.deividst.api.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Raspberry Awards Analyzer API")
                        .version("1.0")
                        .description("API responsável por realizar a análise do intervalo de vitórias do raspberry awards"));
    }
}