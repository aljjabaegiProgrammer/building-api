package com.geonlee.api.domin.member.repository;

import com.geonlee.api.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-08-25
 */
@Repository
public interface MemberSpecificationRepository extends JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {

    @EntityGraph(attributePaths = "authority")
    List<Member> findAll(Sort sort);

    @EntityGraph(attributePaths = "authority")
    Page<Member> findAll(Pageable pageable);


}
