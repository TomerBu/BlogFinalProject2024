package edu.tomerbu.blogfinalproject2024.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Blog Project",
        version = "1.0",
        description = "API for Our Blog",
        contact = @Contact(
                name = "TomerBu",
                url = "https://www.springone.io",
                email = "Tomerbu@gmail.com"
        ),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.txt")
))
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApi3Config {
}