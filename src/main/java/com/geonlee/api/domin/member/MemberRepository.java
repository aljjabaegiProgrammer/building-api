package com.geonlee.api.domin.member;

import com.geonlee.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
}
