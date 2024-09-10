package com.geonlee.api.domin.login.record;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @author GEONLEE
 * @since 2024-09-06
 */
@Schema(description = "로그인 요청")
public record LoginRequest(
        @NotNull
        @Schema(description = "회원 ID", example = "member05")
        String memberId,
        @NotNull
        @Schema(description = "패스워드", example = "1234")
        String password
) {
}
