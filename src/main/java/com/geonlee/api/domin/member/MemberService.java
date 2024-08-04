package com.geonlee.api.domin.member;

import com.geonlee.api.domin.member.record.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
public interface MemberService {
    MemberSearchResponse getMemberById(String memberId);

    List<MemberSearchResponse> getMembers();

    @Transactional
    MemberCreateResponse createMember(MemberCreateRequest parameter);

    @Transactional
    MemberModifyResponse modifyMember(MemberModifyRequest parameter);

    @Transactional
    Long deleteMember(String memberId);
}
