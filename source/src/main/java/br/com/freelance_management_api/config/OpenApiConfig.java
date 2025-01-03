package br.com.freelance_management_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Freelance Management API",
                version = "1.0",
                description = "API para o gerenciamento de freelancers, projetos e cadastros"
        )
)
public class OpenApiConfig {

        @Bean
        public MessageSource messageSource() {
                ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
                messageSource.setBasename("messages");
                messageSource.setDefaultEncoding("UTF-8");
                return messageSource;
        }

        @Bean
        public LocaleResolver localeResolver() {
                return new FixedLocaleResolver(new Locale("pt", "BR"));
        }

        @Bean
        public OpenApiCustomizer customizeOpenAPI() {
                return openApi -> openApi.getPaths().values().forEach(pathItem ->
                        pathItem.readOperations().forEach(operation ->
                                operation.addParametersItem(new HeaderParameter()
                                        .name("Accept-Language")
                                        .description("Idioma preferido para a resposta")
                                        .required(false)
                                        .example("pt-BR")
                                )
                        )
                );
        }
}
