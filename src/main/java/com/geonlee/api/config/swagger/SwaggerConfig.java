package com.geonlee.api.config.swagger;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.response.ErrorResponse;
import com.geonlee.api.config.message.MessageConfig;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SwaggerConfig {

    private final MessageConfig messageConfig;

    @Bean
    public GroupedOpenApi version1APi() {
        return GroupedOpenApi.builder()
                .group("v1.0")
                .pathsToMatch("/v1/**")
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi version2APi() {
        return GroupedOpenApi.builder()
                .group("v2.0")
                .pathsToMatch("/v2/**")
                .build();
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            ApiResponses apiResponses = operation.getResponses();
            if (apiResponses.containsKey("200")) {
                ApiResponse normalResponse = operation.getResponses().get("200");
                normalResponse.description("정상 응답");
                apiResponses.put("OK", normalResponse);
                apiResponses.remove("200");
            }
            apiResponses.addApiResponse("ERR_CE_01", getInvalidParameterResponse());
            return operation;
        };
    }

    public ApiResponse getInvalidParameterResponse() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setDescription("유효하지 않은 인자가 전달됨");
        addContent(apiResponse, ErrorCode.INVALID_PARAMETER);
        return apiResponse;
    }

    //    @SuppressWarnings("rawtypes")
    private void addContent(ApiResponse apiResponse, ErrorCode errorCode) {
        Content content = new Content();
        MediaType mediaType = new MediaType();
        Schema<Object> schema = new Schema<>();
        schema.$ref("#/components/schemas/ErrorResponse");
        mediaType.schema(schema).example(ErrorResponse.builder()
                .status(messageConfig.getCode(errorCode))
                .message(messageConfig.getMessage(errorCode))
                .build());
        content.addMediaType("application/json", mediaType);
        apiResponse.setContent(content);
    }
}
