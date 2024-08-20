package com.geonlee.api.domin.member.record;

import com.geonlee.api.entity.enumeration.UseYn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Schema(description = "회원 수정 요청 Record")
public record MemberModifyRequest(
        @Schema(description = "회원 id (최대 30자)", example = "member01")
        @NotEmpty
        String memberId,
        @Schema(description = "사용 여부", example = "Y")
        UseYn useYn,
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글/영문만 가능합니다.")
        @Schema(description = "회원 명", example = "이건")
        String memberName,
        @Pattern(regexp = "^[A-Z_]+$", message = "권한 코드가 올바르지 않습니다.")
        @Schema(description = "권한 코드", example = "ROLE_ADMIN")
        String authorityCode
) {
}
