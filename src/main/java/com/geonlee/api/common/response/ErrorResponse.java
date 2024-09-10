package com.geonlee.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

/**
 * @author GEONLEE
 * @since 2024-07-30
 */
@Builder
public record ErrorResponse(
        String status,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String detailMessage
) {
}
