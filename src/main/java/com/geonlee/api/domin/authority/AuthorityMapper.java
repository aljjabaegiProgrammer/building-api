package com.geonlee.api.domin.authority;

import com.geonlee.api.common.converter.Converter;
import com.geonlee.api.common.mapStruct.GenericMapper;
import com.geonlee.api.domin.authority.record.AuthoritySearchResponse;
import com.geonlee.api.domin.member.record.MemberSearchResponse;
import com.geonlee.api.entity.Authority;
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
public interface AuthorityMapper extends GenericMapper<AuthoritySearchResponse, Authority> {

    @Mappings({
            @Mapping(target = "authorityName", source = "authority.authorityName"),
            @Mapping(target = "createDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getCreateDate()))"),
//            @Mapping(target = "updateDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getUpdateDate()))")
    })
    MemberSearchResponse toMemberRecord(Member entity);

    @Mappings({
            @Mapping(target = "createDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getCreateDate()))"),
//            @Mapping(target = "updateDate", expression = "java(Converter.localDateTimeToFormattedString(entity.getUpdateDate()))")
    })
    AuthoritySearchResponse toRecord(Authority entity);
}
