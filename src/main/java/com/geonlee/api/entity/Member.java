package com.geonlee.api.entity;

import com.geonlee.api.domin.member.record.MemberModifyRequest;
import com.geonlee.api.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Member extends BaseEntity implements Persistable<String> {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pw")
    private String password;

    @Column(name = "member_nm")
    private String memberName;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "authority_cd")
    private String authorityCode;

    @Override
    public String getId() {
        return this.memberId;
    }

    @Override
    public boolean isNew() {
        return getCreateDate() == null;
    }

    public void updateFromRecord(MemberModifyRequest parameter) {
        this.memberName = parameter.memberName();
        this.useYn = parameter.useYn();
    }
}
