package com.geonlee.api.domin.member.service;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.exception.custom.ServiceException;
import com.geonlee.api.domin.member.MemberMapper;
import com.geonlee.api.domin.member.MemberService;
import com.geonlee.api.domin.member.record.*;
import com.geonlee.api.domin.member.repository.MemberQueryMethodRepository;
import com.geonlee.api.entity.Member;
import com.geonlee.api.entity.QAuthority;
import com.geonlee.api.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author GEONLEE
 * @since 2024-09-02
 */
@Service
@RequiredArgsConstructor
public class MemberQueryDslServiceImpl implements MemberService {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
    private final MemberQueryMethodRepository memberQueryMethodRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MemberSearchResponse getMemberById(String memberId) {
        QMember qMember = QMember.member;
        Optional<Member> optionalMember = Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectFrom(qMember)
                .where(qMember.memberId.eq(memberId))
                .fetchOne());
        if (optionalMember.isEmpty()) {
            throw new ServiceException(ErrorCode.NO_DATA, "회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        return memberMapper.toRecord(member);
    }

    @Override
    public List<MemberSearchResponse> getMembersByMemberName(String memberName) {
        return null;
    }

    @Override
    public List<MemberSearchResponse> getMembers() {
        QMember qMember = QMember.member;
        List<Member> members = new JPAQueryFactory(entityManager)
                .selectFrom(qMember)
                .setHint("jakarta.persistence.loadgraph"
                        , entityManager.getEntityGraph("memberGraph"))
                .fetch();
        return memberMapper.toRecordList(members);
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
    public Long deleteMember(String memberId) {
        return null;
    }
}
