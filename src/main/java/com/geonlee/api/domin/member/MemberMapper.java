package com.geonlee.api.domin.member;

import com.geonlee.api.common.mapStruct.GenericMapper;
import com.geonlee.api.domin.member.record.MemberSearchResponse;
import com.geonlee.api.entity.Member;
import io.micrometer.common.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author GEONLEE
 * @since 2024-08-08
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends GenericMapper<MemberSearchResponse, Member> {
    @Mapping(target = "memberId", source = "memberId", qualifiedByName = "toUpperCase")
    MemberSearchResponse toRecord(Member entity);

    @Named("toUpperCase")
    default String toUpperCase(String text) {
        return StringUtils.isEmpty(text) ? null : text.toUpperCase();
    }
}
