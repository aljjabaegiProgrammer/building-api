package com.geonlee.api.domin.member.service;

import com.geonlee.api.common.converter.Converter;
import com.geonlee.api.domin.member.MemberMapper;
import com.geonlee.api.domin.member.MemberService;
import com.geonlee.api.domin.member.record.*;
import com.geonlee.api.entity.Authority;
import com.geonlee.api.entity.Member;
import com.geonlee.api.entity.enumeration.UseYn;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-08-23
 */
@Service
@RequiredArgsConstructor
public class MemberServiceCriteriaImpl implements MemberService {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public MemberSearchResponse getMemberById(String memberId) {
        //CriteriaBuilder 인스턴스 생성
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        /*
         * >리턴 타입 정의
         * 반환타입 명확한 경우
         * CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
         *
         * 반환타입 불명확한 경우 Object, Object[] 사용
         * CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(Object.class);
         * CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
         *
         * Tuple 사용
         * CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
         * */
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        /*
         * Record 로 query 를 바로 매핑할 경우
         * CriteriaQuery<MemberSearchResponse> criteriaQuery = criteriaBuilder.createQuery(MemberSearchResponse.class);
         * */
        Root<Member> rootMember = criteriaQuery.from(Member.class);
        Join<Member, Authority> authorityJoin = rootMember.join("authority", JoinType.LEFT);


        Predicate predicate = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(memberId)) {
            predicate = criteriaBuilder.equal(rootMember.get("memberId"), memberId);
            /*
             * 복수 조건
             * List<Predicate> predicates = new ArrayList<>();
             * predicates.add(criteriaBuilder.equal(rootMember.get("memberId"), memberId));
             * */
        }
        criteriaQuery.select(criteriaBuilder.tuple(
                        rootMember.get("memberId").alias("memberId"),
                        rootMember.get("memberName").alias("memberName"),
                        rootMember.get("useYn").alias("useYn"),
                        rootMember.get("createDate").alias("createDate"),
                        rootMember.get("updateDate").alias("updateDate"),
                        authorityJoin.get("authorityName").alias("authorityName"),
                        criteriaBuilder.selectCase()
                                .when(criteriaBuilder.equal(rootMember.get("useYn"), UseYn.Y), "정상 회원")
                                .when(criteriaBuilder.equal(rootMember.get("useYn"), UseYn.N), "잠긴 회원")
                                .otherwise("알수 없음")
                ))
//                .where(predicates.toArray(new Predicate[0]))
                .where(predicate)
                .orderBy(criteriaBuilder.asc(rootMember.get("memberName")));

        /*
         * Java 객체로 매핑
         * criteriaQuery.select(criteriaBuilder.construct(MemberSearchResponse.class
         *                   , rootMember.get("memberId")
         *                   , rootMember.get("memberName")))
         *               .where(predicates.toArray(new Predicate[0]));
         * */

        /*
         * 복수 컬럼 조회 multiselect
         * criteriaQuery.multiselect(rootMember.get("memberId")
         *                         , rootMember.get("memberName"))
         *              .where(predicates.toArray(new Predicate[0]));
         *
         * */

        Tuple tuple = entityManager.createQuery(criteriaQuery).getSingleResult();
        /*
         * 복수 결과 리턴
         * List<Tuple> tupleList = entityManager.createQuery(criteriaQuery).getResultList();
         *
         * */

        return MemberSearchResponse.builder()
                .memberId(tuple.get("memberId", String.class))
                .memberName(tuple.get("memberName", String.class))
                .useYn(tuple.get("useYn", UseYn.class).name())
                .authorityName(tuple.get("authorityName", String.class))
                .createDate(Converter.localDateTimeToFormattedString(tuple.get("createDate", LocalDateTime.class)))
                .updateDate(Converter.localDateTimeToFormattedString(tuple.get("updateDate", LocalDateTime.class)))
                .build();
    }

    @Override
    public List<MemberSearchResponse> getMembersByMemberName(String memberName) {
        return null;
    }

    @Override
    public List<MemberSearchResponse> getMembers() {
        return null;
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
