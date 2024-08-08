package com.geonlee.api.domin.member.record;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
public record MemberCreateRequest(
        @NotEmpty(message = "id 는 필수 입니다.")
        String memberId,
        String password,
        String useYn,
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글/영문만 가능합니다.")
        String memberName
) {
}
