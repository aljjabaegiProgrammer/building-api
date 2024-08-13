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
@Schema(description = "회원 조회 응답 Record")
public record MemberSearchResponse(
        @Schema(description = "회원 ID", example = "member01")
        String memberId,
        @Schema(description = "회원 명", example = "회원1")
        String memberName,
        @Schema(description = "사용 여부[Y/N]", example = "Y")
        String useYn,
        @Schema(description = "생성일시", example = "2024-08-12 00:00:00")
        String createDate,
        @Schema(description = "수정일시", example = "2024-08-12 00:00:00")
        String updateDate
) {
}
