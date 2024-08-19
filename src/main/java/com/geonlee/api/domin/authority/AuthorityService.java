package com.geonlee.api.domin.authority;

import com.geonlee.api.domin.authority.record.AuthoritySearchResponse;
import com.geonlee.api.domin.member.record.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-08-19
 */
public interface AuthorityService {
    AuthoritySearchResponse getAuthorityById(String authorityCode);

    List<AuthoritySearchResponse> getAuthorities();

    @Transactional
    MemberCreateResponse createAuthority(MemberCreateRequest parameter);

    @Transactional
    MemberModifyResponse modifyAuthority(MemberModifyRequest parameter);

    @Transactional
    Long deleteAuthority(String memberId);
}
