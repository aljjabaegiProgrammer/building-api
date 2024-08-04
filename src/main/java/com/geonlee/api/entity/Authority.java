package com.geonlee.api.entity;

import com.geonlee.api.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
