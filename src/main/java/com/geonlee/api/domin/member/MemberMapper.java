package com.geonlee.api.domin.member;

import com.geonlee.api.domin.member.record.MemberSearchResponse;
import com.geonlee.api.entity.Member;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotEmpty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-08-08
 */
@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mapping(target = "memberId", source = "memberId", qualifiedByName = "toUpperCase")
    MemberSearchResponse toRecord(Member entity);

    List<MemberSearchResponse> toRecordList(List<Member> entity);

    @Named("toUpperCase")
    default String toUpperCase(String text) {
        return StringUtils.isEmpty(text) ? null : text.toUpperCase();
    }
}
