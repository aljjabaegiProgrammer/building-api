package com.geonlee.api.domin.member;

import com.geonlee.api.domin.authority.AuthorityRepository;
import com.geonlee.api.domin.member.record.*;
import com.geonlee.api.entity.Authority;
import com.geonlee.api.entity.Member;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;

    private final MemberMapper memberMapper;

    @Override
    public MemberSearchResponse getMemberById(String memberId) {
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원 ID 가 존재하지 않습니다. -> " + memberId));
        return memberMapper.toRecord(memberEntity);
    }

    @Override
    public List<MemberSearchResponse> getMembers() {
        return memberMapper.toRecordList(memberRepository.findAll());
    }

    @Override
    public MemberCreateResponse createMember(MemberCreateRequest parameter) {
        if (memberRepository.existsById(parameter.memberId())) {
            throw new EntityExistsException("이미 존재하는 ID 입니다. -> " + parameter.memberId());
        }
        Authority authorityEntity = authorityRepository.findById(parameter.authorityCode())
                .orElseThrow(() -> new EntityNotFoundException(
                                "권한코드가 존재하지 않습니다. -> " + parameter.authorityCode()
                        )
                );
        Member newMember = memberMapper.toEntity(parameter);
        newMember.setAuthority(authorityEntity);
        authorityEntity.getMembers().add(newMember);
        memberRepository.save(newMember);
        return memberMapper.toCreateRecord(newMember);
    }

    @Override
    public MemberModifyResponse modifyMember(MemberModifyRequest parameter) {
        Member memberEntity = memberRepository.findById(parameter.memberId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "수정할 회원 ID 가 존재하지 않습니다. -> " + parameter.memberId())
                );
        Authority authorityEntity = authorityRepository.findById(parameter.authorityCode())
                .orElseThrow(() -> new EntityNotFoundException(
                        "수정할 권한코드가 존재하지 않습니다. -> " + parameter.authorityCode())
                );
        memberEntity.updateFromRecord(parameter, authorityEntity);
        memberRepository.saveAndFlush(memberEntity);
        return memberMapper.toModifyRecord(memberEntity);
    }

    @Override
    public Long deleteMember(String memberId) {
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "삭제할 회원 ID 가 존재하지 않습니다. -> " + memberId)
                );
        memberRepository.delete(memberEntity);
        return 1L;
    }
}
