package com.geonlee.api.domin.authority;

import com.geonlee.api.domin.authority.record.AuthoritySearchResponse;
import com.geonlee.api.domin.member.record.MemberCreateRequest;
import com.geonlee.api.domin.member.record.MemberCreateResponse;
import com.geonlee.api.domin.member.record.MemberModifyRequest;
import com.geonlee.api.domin.member.record.MemberModifyResponse;
import com.geonlee.api.entity.Authority;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-08-19
 */
@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper = Mappers.getMapper(AuthorityMapper.class);

    @Override
    @Transactional
    public AuthoritySearchResponse getAuthorityById(String authorityCode) {
        Authority authorityEntity = authorityRepository.findById(authorityCode)
                .orElseThrow(() -> new EntityNotFoundException("권한 코드가 존재하지 않습니다. -> " + authorityCode));
        return authorityMapper.toRecord(authorityEntity);
    }

    @Override
    @Transactional
    public List<AuthoritySearchResponse> getAuthorities() {
        return authorityMapper.toRecordList(authorityRepository.findAll());
    }

    @Override
    public MemberCreateResponse createAuthority(MemberCreateRequest parameter) {
        return null;
    }

    @Override
    public MemberModifyResponse modifyAuthority(MemberModifyRequest parameter) {
        return null;
    }

    @Override
    public Long deleteAuthority(String memberId) {
        return null;
    }
}
