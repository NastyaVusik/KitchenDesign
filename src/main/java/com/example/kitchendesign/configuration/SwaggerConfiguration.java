package com.example.kitchendesign.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    GroupedOpenApi publicOpenApi() {

        return GroupedOpenApi.builder()
                .group("public apis")
                .pathsToMatch("/**")
                .build();
    }


    //    http://localhost:8080/swagger-ui.html
    @Bean
    public OpenAPI KitchenDesignOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("API project KitchenDesign")
                        .version("API version 2.5.0")
                        .description("Design. 3D visualisation. Calculation of kitchen furniture"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));

    }
}
