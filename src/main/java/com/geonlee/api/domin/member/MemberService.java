package com.geonlee.api.domin.member;

import com.geonlee.api.domin.member.record.*;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
public interface MemberService {
    MemberSearchResponse getMemberById(String memberId);
    List<MemberSearchResponse> getMembers();

    MemberCreateResponse createMember(MemberCreateRequest parameter);

    MemberModifyResponse modifyMember(MemberModifyRequest parameter);

    Long deleteMember(MemberDeleteRequest parameter);
}
