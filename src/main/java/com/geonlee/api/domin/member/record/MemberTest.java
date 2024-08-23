package com.geonlee.api.domin.member.record;

import com.geonlee.api.entity.enumeration.UseYn;

/**
 * @author GEONLEE
 * @since 2024-08-22
 */
public record MemberTest(
        String memberId,
        String useYn,
        String createDate,
        String updateDate
) {
}
