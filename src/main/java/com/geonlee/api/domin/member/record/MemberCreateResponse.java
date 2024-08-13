package com.geonlee.api.domin.member.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Builder
@Schema(description = "회원 추가 응답 Record")
public record MemberCreateResponse(
        @Schema(description = "회원 id (최대 30자)", example = "member01")
        String memberId,
        @Schema(description = "회원 명", example = "이건")
        String memberName,
        @Schema(description = "사용 여부", example = "Y")
        String useYn,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "생성 일시", example = "2024-08-13 00:00:00")
        LocalDateTime createDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "수정 일시", example = "2024-08-13 00:00:00")
        LocalDateTime updateDate
) {
}
