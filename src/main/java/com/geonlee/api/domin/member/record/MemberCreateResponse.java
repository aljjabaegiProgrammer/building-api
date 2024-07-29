package com.geonlee.api.domin.member.record;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
public record MemberCreateResponse(
        String memberId,
        String memberName,
        String createDate,
        String updateDate
) {
}
