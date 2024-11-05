package com.Formation.formationapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class SwaggerConfig {

     @Bean
    public OpenAPI formationOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Gestion de Formation")
                .description("API pour la gestion des ressources du centre de formation incluant les Formation, Etudiants, Formateurs et Classes")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Équipe Formation")
                    .email("contact@formation.com")
                    .url("https://formation.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
    
}
