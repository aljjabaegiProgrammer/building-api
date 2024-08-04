package com.geonlee.api.domin.member.record;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
public record MemberModifyRequest(
        String memberId,
        String useYn,
        String memberName
) {
}
