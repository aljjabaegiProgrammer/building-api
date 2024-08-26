package com.geonlee.api.domin.member.repository;

import com.geonlee.api.domin.member.record.MemberSearchResponse;
import com.geonlee.api.entity.Member;
import com.geonlee.api.entity.enumeration.UseYn;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Repository
public interface MemberJPQLRepository extends JpaRepository<Member, String> {

    @Query(value = "SELECT m FROM member m JOIN FETCH m.authority")
    @Nonnull
    List<Member> findAll();

    @Query(value = "select m from member m where m.memberId = :memberId")
    Member findByMemberId(String memberId);

    // memberName 으로 조회
//    @Query("select m from member m where m.memberName = ?1")
//    @Query("select m from member m where m.memberName = '이건'")
//    @Query(value = "select * from member where member_nm = :memberName", nativeQuery = true)
    @Query(name = "Member.findByMemberName", nativeQuery = true)
    List<MemberSearchResponse> findMembersByMemberName(String memberName);
    @Query(value = """
             select new com.geonlee.api.domin.member.record.MemberSearchResponse(
                 m.memberId
               , m.memberName
               , CAST(m.useYn AS string)
               , m.authority.authorityName
               , CAST(FUNCTION('TO_CHAR', m.createDate, 'YYYY-MM-DD HH24:MI:SS') AS string)
               , CAST(FUNCTION('TO_CHAR', m.createDate, 'YYYY-MM-DD HH24:MI:SS') AS string)) 
             from member m 
            where m.memberName = :memberName
             """)
    List<MemberSearchResponse> getMemberToRecord(String memberName);

    // memberId (equal) 와 memberName (like) 을 And 조건으로 조회
    @Query("""
            select m from member m where m.memberId = :memberId
                                     and m.memberName like %:memberName%
            """)
    List<Member> findMembersByMemberIdAndMemberName(@Param("memberId") String memberId,
                                                    @Param("memberName") String memberName);

    // memberId (equal) 와 memberName (like) 을 Or 조건으로 조회
    @Query("select m from member m where m.memberId = ?1 " +
            "or m.memberName like concat('%', ?2, '%')")
    List<Member> findMembersByMemberIdOrMemberName(String memberId, String memberName);

    // Enum type 조회
//    @Query("select m from member m where m.useYn = ?1")
    @Query("select m from member m where m.useYn = com.geonlee.api.entity.enumeration.UseYn.N")
    List<Member> findMembersByUseYn(UseYn useYn);

    // 연관관계인 Authority 의 authorityName 으로 조회
    @Query("select m from member m where m.authority.authorityName = ?1")
    List<Member> findMembersByAuthorityAuthorityName(String authorityName);

    // BaseEntity 의 createDate 를 between 조건으로 조회
//    @Query("select m from member m where m.createDate between ?1 and ?2")
//    @Query("select m from member m where m.createDate between {d '2024-08-01'} and {d '2024-08-20'}")
    @Query("""
            select m from member m where m.createDate between {ts '2024-08-19 21:25:00.000000001'}
                                                          and {ts '2024-08-19 21:30:00.000000001'}
            """)
    List<Member> findMembersByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
