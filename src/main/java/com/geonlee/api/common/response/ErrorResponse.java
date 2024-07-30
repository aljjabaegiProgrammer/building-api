package com.geonlee.api.common.response;

import lombok.Builder;

/**
 * @author GEONLEE
 * @since 2024-07-30
 */
@Builder
public record ErrorResponse(
        String status,
        String message,
        String detailMessage
) {
}
