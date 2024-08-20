package com.geonlee.api.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * @author GEONLEE
 * @since 2024-08-20
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CodeId implements Serializable {

    @Column(name = "code_group_id")
    private String codeGroupId;

    @Column(name = "code_id")
    private String codeId;
}
