package com.geonlee.api.config.swagger;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GEONLEE
 * @since 2024-08-12
 */
@OpenAPIDefinition(
        info = @Info(title = "GEONLEE API Documentation"
                , version = "v1.0.0"
                , description = """
                - Springboot 3.3.1 기반 JPA 활용 API
                """
                , termsOfService = "https://velog.io/@geonlee/posts"
                , license = @License(name = "Apache License Version 2.0"
                , url = "https://www.apache.org/licenses/LICENSE-2.0")
                , contact = @Contact(name = "GEONLEE"
                , email = "geonlee@email.com")
        ),
        servers = {
                @Server(url = "/my-api", description = "API 구축 테스트 서버")
        },
        externalDocs = @ExternalDocumentation(description = "Swagger reference", url = "https://springdoc.org/properties.html")
)
@Configuration
public class SwaggerConfig {
        @Bean
        public GroupedOpenApi version1APi() {
                return GroupedOpenApi.builder()
                        .group("v1.0")
                        .pathsToMatch("/v1/**")
                        .build();
        }

        @Bean
        public GroupedOpenApi version2APi() {
                return GroupedOpenApi.builder()
                        .group("v2.0")
                        .pathsToMatch("/v2/**")
                        .build();
        }
}
