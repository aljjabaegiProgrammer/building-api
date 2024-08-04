package com.geonlee.api.domin.member;

import com.geonlee.api.domin.member.record.*;
import com.geonlee.api.entity.Member;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext    // EntityManagerFactory가 DI 할 수 있도록 어노테이션 설정
    private EntityManager em;

    @Override
    public MemberSearchResponse getMemberById(String memberId) {
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원 ID 가 존재하지 않습니다. -> " + memberId));
        return MemberSearchResponse.builder()
                .memberId(memberEntity.getMemberId())
                .memberName(memberEntity.getMemberName())
                .createDate(memberEntity.getCreateDate())
                .updateDate(memberEntity.getUpdateDate())
                .build();
    }

    @Override
    public List<MemberSearchResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(memberEntity -> MemberSearchResponse.builder()
                        .memberId(memberEntity.getMemberId())
                        .memberName(memberEntity.getMemberName())
                        .createDate(memberEntity.getCreateDate())
                        .updateDate(memberEntity.getUpdateDate())
                        .build())
                .toList();
    }

    @Override
    public MemberCreateResponse createMember(MemberCreateRequest parameter) {
        if (memberRepository.existsById(parameter.memberId())) {
            throw new EntityExistsException("이미 존재하는 ID 입니다. -> " + parameter.memberId());
        }
        Member newMember = Member.builder()
                .memberId(parameter.memberId())
                .password(parameter.password())
                .memberName(parameter.memberName())
                .useYn(parameter.useYn())
                .build();
        memberRepository.save(newMember);
        return MemberCreateResponse.builder()
                .memberId(newMember.getMemberId())
                .memberName(newMember.getMemberName())
                .useYn(newMember.getUseYn())
                .createDate(newMember.getCreateDate())
                .updateDate(newMember.getUpdateDate())
                .build();
    }

    @Override
    public MemberModifyResponse modifyMember(MemberModifyRequest parameter) {
        Member memberEntity = memberRepository.findById(parameter.memberId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "수정할 회원 ID 가 존재하지 않습니다. -> " + parameter.memberId())
                );
        memberEntity.updateFromRecord(parameter);
        memberRepository.saveAndFlush(memberEntity);
        return MemberModifyResponse.builder()
                .memberId(memberEntity.getMemberId())
                .memberName(memberEntity.getMemberName())
                .useYn(memberEntity.getUseYn())
                .createDate(memberEntity.getCreateDate())
                .updateDate(memberEntity.getUpdateDate())
                .build();
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
