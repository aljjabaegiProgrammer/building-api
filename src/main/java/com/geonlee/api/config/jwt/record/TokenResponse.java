package com.geonlee.api.config.jwt.record;

import lombok.Builder;

/**
 * @author GEONLEE
 * @since 2024-09-06
 */
@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
