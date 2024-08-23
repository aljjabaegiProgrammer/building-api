package com.geonlee.api.domin.member;

import com.geonlee.api.common.converter.Converter;
import com.geonlee.api.common.mapStruct.GenericMapper;
import com.geonlee.api.domin.member.record.MemberCreateRequest;
import com.geonlee.api.domin.member.record.MemberCreateResponse;
import com.geonlee.api.domin.member.record.MemberModifyResponse;
import com.geonlee.api.domin.member.record.MemberSearchResponse;
import com.geonlee.api.entity.Member;
import io.micrometer.common.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * @author GEONLEE
 * @since 2024-08-08
 */
@Mapper(componentModel = "spring", imports = Converter.class)
public interface MemberMapper extends GenericMapper<MemberSearchResponse, Member> {
    @Mappings({
            @Mapping(target = "memberId", source = "memberId", qualifiedByName = "toUpperCase"),
            @Mapping(target = "authorityName", source = "authority.authorityName"),
            @Mapping(target = "createDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getCreateDate()))"),
//            @Mapping(target = "updateDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getUpdateDate()))")
    })
    MemberSearchResponse toRecord(Member entity);


    @Mappings({
            @Mapping(target = "memberId", source = "memberId", qualifiedByName = "toUpperCase"),
            @Mapping(target = "authorityName", source = "authority.authorityName"),
            @Mapping(target = "createDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getCreateDate()))"),
//            @Mapping(target = "updateDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getUpdateDate()))")
    })
    MemberCreateResponse toCreateRecord(Member entity);

    @Mappings({
            @Mapping(target = "memberId", source = "memberId", qualifiedByName = "toUpperCase"),
            @Mapping(target = "authorityName", source = "authority.authorityName"),
            @Mapping(target = "createDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getCreateDate()))"),
//            @Mapping(target = "updateDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getUpdateDate()))")
    })
    MemberModifyResponse toModifyRecord(Member entity);


    Member toEntity(MemberCreateRequest record);

    @Named("toUpperCase")
    default String toUpperCase(String text) {
        return StringUtils.isEmpty(text) ? null : text.toUpperCase();
    }
}
