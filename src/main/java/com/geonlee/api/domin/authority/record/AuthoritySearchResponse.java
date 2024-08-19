package com.geonlee.api.domin.authority.record;

import com.geonlee.api.domin.member.record.MemberSearchResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-08-19
 */
@Schema(description = "권한 조회 응답")
public record AuthoritySearchResponse(
        @Schema(description = "권한 코드", example = "ROLE_ADMIN")
        String authorityCode,
        @Schema(description = "권한 명", example = "관리자")
        String authorityName,
        @Schema(description = "생성일시", example = "2024-08-12 00:00:00")
        String createDate,
        @Schema(description = "수정일시", example = "2024-08-12 00:00:00")
        String updateDate,
        @Schema(description = "권한을 갖는 회원 리스트")
        List<MemberSearchResponse> members
) {
}
