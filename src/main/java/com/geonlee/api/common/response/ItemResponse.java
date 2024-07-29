package com.geonlee.api.common.response;

import lombok.Builder;

/**
 * @author GEONLEE
 * @since 2024-07-29
 */
@Builder
public record ItemResponse<T>(
        String status,
        String message,
        T item
) {
}
