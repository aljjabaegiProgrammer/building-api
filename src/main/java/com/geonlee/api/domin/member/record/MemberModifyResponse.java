package com.geonlee.api.domin.member.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Builder
public record MemberModifyResponse(
        String memberId,
        String memberName,
        String useYn,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updateDate
) {
}
