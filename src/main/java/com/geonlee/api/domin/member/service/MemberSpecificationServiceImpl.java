package com.geonlee.api.domin.member.service;

import com.geonlee.api.domin.member.MemberMapper;
import com.geonlee.api.domin.member.record.MemberSearchResponse;
import com.geonlee.api.domin.member.repository.MemberSpecificationRepository;
import com.geonlee.api.domin.member.specification.MemberSpecification;
import com.geonlee.api.entity.Member;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-08-25
 */
@Service
@RequiredArgsConstructor
public class MemberSpecificationServiceImpl {

    private final MemberSpecificationRepository memberRepository;
    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);

    public List<MemberSearchResponse> getMemberByIdAndName(String memberId, String memberName) {
        List<Member> members = memberRepository.findAll(
                MemberSpecification.memberId(memberId)
                        .and(MemberSpecification.memberName(memberName))
        );
        return memberMapper.toRecordList(members);
    }

    public List<MemberSearchResponse> getMemberById(String memberId) {
        List<Member> members = memberRepository.findAll(
                MemberSpecification.memberId(memberId)
        );
        return memberMapper.toRecordList(members);
    }

    @Transactional
    public List<MemberSearchResponse> getMemberOrderByMemberNameAsc() {
        Sort sort = Sort.by(Sort.Order.desc("memberName"));
//        List<Member> members = memberRepository.findAll(sort);
//        return memberMapper.toRecordList(members);
        return null;
    }

    @Transactional
    public List<MemberSearchResponse> getMemberOrderByMemberNameAscWithPaging() {
        Sort sort = Sort.by(Sort.Order.desc("memberName"));
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Member> members = memberRepository.findAll(pageable);
        return memberMapper.toRecordList(members.getContent());
    }

    @Transactional
    public List<MemberSearchResponse> getMemberOrderByMemberNameAscWithPagingSlice() {
        Sort sort = Sort.by(Sort.Order.desc("memberName"));
        Pageable pageable = PageRequest.of(0, 10, sort);
        Slice<Member> members = memberRepository.findAll(pageable);
        return memberMapper.toRecordList(members.getContent());
    }
}
