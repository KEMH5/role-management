package com.corp.springsecurityasymetricencryption.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Spring security JWT asymmetric Encryption demo",
                        email = "contact@kemh.com"
                ),
                description = "OpenAPI documentation for Spring Security project",
                title = "OpenAPI Specification",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = ""
                ),
                termsOfService = ""
        ),
        servers ={
                @Server(
                        url = "http://localhost:8080",
                        description = "Local ENV"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://prod.url"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
