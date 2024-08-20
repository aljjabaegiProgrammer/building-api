package com.geonlee.api.entity;

import com.geonlee.api.entity.base.BaseEntity;
import com.geonlee.api.entity.enumeration.UseYn;
import com.geonlee.api.entity.key.CodeId;
import jakarta.persistence.*;
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
@Entity(name = "code")
//@IdClass(CodeId.class)
public class Code extends BaseEntity {

    @EmbeddedId
    private CodeId codeId;

//    @Id
//    @Column(name = "codeGroupId")
//    private String codeGroupId;
//
//    @Id
//    @Column(name = "codeId")
//    private String codeId;

    @Column(name = "code_nm")
    private String codeName;

    @Column(name = "code_desc")
    private String codeDescription;

    @Column(name = "code_order")
    private Short codeOrder;

    @Column(name = "use_yn")
    @Enumerated(EnumType.STRING)
    private UseYn useYn;
}
