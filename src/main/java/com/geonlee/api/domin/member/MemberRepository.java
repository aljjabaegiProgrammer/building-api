package com.geonlee.api.domin.member;

import com.geonlee.api.entity.Member;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query(value = "SELECT m FROM member m")
    @Nonnull
    List<Member> findAll();

    @Query(value = "SELECT m FROM member m where m.memberId = :memberId")
    Member findByMemberId(String memberId);
}
