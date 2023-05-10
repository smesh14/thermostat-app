package com.thermostat.user.service.utils;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .packagesToScan("com.thermostat.user.service.api")
                .group("com.thermostat").build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        final SecurityRequirement securityItem = new SecurityRequirement().addList("Bearer");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer", securityScheme))
                .addSecurityItem(securityItem);
    }
}
