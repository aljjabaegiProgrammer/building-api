package com.geonlee.api.domin.member.repository;

import com.geonlee.api.entity.Member;
import com.geonlee.api.entity.enumeration.UseYn;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Repository
public interface MemberQueryMethodRepository extends JpaRepository<Member, String> {

    @EntityGraph(value = "memberGraph")
    @Nonnull
    List<Member> findAll();
    // memberName 으로 조회
    List<Member> findByMemberName(String memberName);

    // memberId (equal) 와 memberName (like) 을 And 조건으로 조회
    List<Member> findByMemberIdAndMemberNameLike(String memberId, String memberName);

    // memberId (equal) 와 memberName (like) 을 Or 조건으로 조회
    List<Member> findByMemberIdOrMemberNameLike(String memberId, String memberName);

    // Enum type 조회
    List<Member> findByUseYn(UseYn useYn);

    // 연관관계인 Authority 의 authorityName 으로 조회
    List<Member> findByAuthorityAuthorityName(String authorityName);

    // BaseEntity 의 createDate 를 between 조건으로 조회
    List<Member> findByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
