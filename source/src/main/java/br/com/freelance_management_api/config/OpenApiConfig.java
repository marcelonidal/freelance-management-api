package br.com.freelance_management_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Freelance Management API",
                version = "1.0",
                description = "API para o gerenciamento de freelancers, projetos e cadastros"
        )
)
public class OpenApiConfig {
}
