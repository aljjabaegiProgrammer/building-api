package com.geonlee.api.domin.member;

import com.geonlee.api.domin.member.record.MemberSearchResponse;
import com.geonlee.api.entity.Member;
import io.micrometer.common.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author GEONLEE
 * @since 2024-08-08
 */
@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mapping(target = "memberId", expression = "java(toUpperCase(entity.getMemberId()))")
    MemberSearchResponse toRecord(Member entity);

    default String toUpperCase(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        return text.toUpperCase();
    }
}
