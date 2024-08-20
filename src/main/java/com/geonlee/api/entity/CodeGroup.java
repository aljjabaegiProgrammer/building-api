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
 * @author GEONLEE
 * @since 2024-08-20
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "code_group")
public class CodeGroup extends BaseEntity {

    @Id
    @Column(name = "code_group_id")
    private String codeGroupId;

    @Column(name = "code_group_nm")
    private String codeGroupName;

    @Column(name = "code_group_desc")
    private String codeGroupDescription;
}
