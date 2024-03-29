package ru.denusariy.plumbing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Plumbing")
                        .description("REST-приложение для учета сантехников в районе и их закрепления за " +
                                "конкретными домами")
                        .version("1.0")
                        .license(new License().name("apache 2.0")
                                .url("https://springdoc.org"))
                        .contact(new Contact().name("Denusariy").email("denusariy@gmail.com")))
                .servers(List.of(
                        new Server().url("http://localhost:7777").description("docker-compose"),
                        new Server().url("http://localhost:8585").description("localhost")));
    }


}
