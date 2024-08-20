package com.geonlee.api.entity;

import com.geonlee.api.domin.member.record.MemberModifyRequest;
import com.geonlee.api.entity.base.BaseEntity;
import com.geonlee.api.entity.enumeration.UseYn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLRestriction;
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
@NamedQueries({
        @NamedQuery(name = "member.findAll", query = "SELECT m FROM member m"),
        @NamedQuery(name = "member.findById"
                , query = "SELECT m FROM member m where m.memberId = :memberId")
})
@SQLRestriction("use_yn = 'Y'")
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

    @Transient
    private String noManagement;

    @Formula("case when login_attempts > 4 then 'Y' else 'N' end")
    private String lockYn;

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

    @PrePersist
    public void prePersist() {
        System.out.println("PrePersist : " + this.getCreateDate());
    }

    @PostPersist
    public void postPersist() {
        System.out.println("PrePersist : " + this.getCreateDate());
    }
}
