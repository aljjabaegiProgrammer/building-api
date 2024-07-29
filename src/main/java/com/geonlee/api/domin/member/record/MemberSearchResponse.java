package com.geonlee.api.domin.member.record;

import lombok.Builder;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
public record MemberSearchResponse(
        String memberId,
        String memberName
) {
}
