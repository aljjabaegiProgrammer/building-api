package com.geonlee.api.common.response;

import lombok.Builder;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-07-29
 */
@Builder
public record ItemsResponse<T>(
        Integer status,
        String message,
        List<T> items
) {
}
