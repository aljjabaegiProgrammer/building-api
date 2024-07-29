package com.geonlee.api.domin.member;

import com.geonlee.api.domin.member.record.*;
import com.geonlee.api.entity.Member;
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

    @Override
    public MemberSearchResponse getMemberById(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원 ID 가 존재하지 않습니다. -> " + memberId));
        return new MemberSearchResponse(member.getMemberId(), member.getMemberName());
    }

    @Override
    public List<MemberSearchResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(memberEntity -> new MemberSearchResponse(
                        memberEntity.getMemberId(),
                        memberEntity.getMemberName()))
                .toList();
    }


    @Override
    public MemberCreateResponse createMember(MemberCreateRequest parameter) {
        return null;
    }

    @Override
    public MemberModifyResponse modifyMember(MemberModifyRequest parameter) {
        return null;
    }

    @Override
    public Long deleteMember(MemberDeleteRequest parameter) {
        return null;
    }
}
