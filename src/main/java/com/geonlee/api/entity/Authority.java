package com.geonlee.api.entity;

import com.geonlee.api.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Authority Entity
 *
 * @author GEONLEE
 * @since 2024-08-01
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "authority")
public class Authority extends BaseEntity {

    @Id
    @Column(name = "authority_cd")
    private String authorityCode;

    @Column(name = "authority_nm")
    private String authorityName;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY)
    private Set<Member> members = new HashSet<>();
}
