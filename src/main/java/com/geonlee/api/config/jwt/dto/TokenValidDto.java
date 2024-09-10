package com.geonlee.api.config.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author GEONLEE
 * @since 2024-09-09
 */
@Getter
@Setter
@AllArgsConstructor
public class TokenValidDto {
    private boolean valid;
    private String userId;
    private String accessToken;
}
