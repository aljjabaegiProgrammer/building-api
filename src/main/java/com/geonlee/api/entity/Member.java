package com.geonlee.api.entity;

import com.geonlee.api.domin.member.record.MemberModifyRequest;
import com.geonlee.api.entity.base.BaseEntity;
import com.geonlee.api.entity.enumeration.UseYn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

/**
 * Member Entity
 *
 * @author GEONLEE
 * @since 2024-07-23<br />
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "member")
/**
 * namedQuery 사용 방법
 * @see https://velog.io/@geonlee/JPA-Annotations#namedquery-namedqueries
 * */
//@NamedQueries({
//        @NamedQuery(name = "member.findAll", query = "SELECT m FROM member m"),
//        @NamedQuery(name = "member.findById"
//                , query = "SELECT m FROM member m where m.memberId = :memberId")
//})
/**
 * 설정 시 Member 를 조회할 때 where 조건으로 use_yn = 'Y'가 추가됨.
 * @see https://velog.io/@geonlee/Hibernate-Annotations#sqlrestriction
 * */
//@SQLRestriction("use_yn = 'Y'")
@NamedEntityGraph(
        name = "Member_graph",
        attributeNodes = {
                @NamedAttributeNode("authority")
        }
)
//@NamedNativeQuery(
//        name = "Member.findByName",
//        query = "SELECT * FROM member WHERE member_nm = :memberName",
//        resultClass = Member.class
//)
public class Member extends BaseEntity implements Persistable<String> {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pw")
    private String password;

    @Column(name = "member_nm")
    private String memberName;

    @Column(name = "use_yn")
    @Enumerated(EnumType.STRING)
    private UseYn useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_cd")
    private Authority authority;

    /**
     * @see https://velog.io/@geonlee/JPA-Annotations#transient
     */
//    @Transient
//    private String noManagement;

    /**
     * @see https://velog.io/@geonlee/Hibernate-Annotations#formula
     */
//    @Formula("case when login_attempts > 4 then 'Y' else 'N' end")
//    private String lockYn;
    @Override
    public String getId() {
        return this.memberId;
    }

    @Override
    public boolean isNew() {
        return getCreateDate() == null;
    }

    public void updateFromRecord(MemberModifyRequest parameter, Authority authority) {
        this.memberName = parameter.memberName();
        this.useYn = parameter.useYn();
        this.authority = authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    /**
     * 생명주기 관련 Annotation
     *
     * @see https://velog.io/@geonlee/JPA-Annotations#%EC%83%9D%EB%AA%85%EC%A3%BC%EA%B8%B0-%EA%B4%80%EB%A0%A8
     */
//    @PrePersist
//    public void prePersist() {
//        System.out.println("PrePersist : " + this.getCreateDate());
//    }
//
//    @PostPersist
//    public void postPersist() {
//        System.out.println("PrePersist : " + this.getCreateDate());
//    }
}
