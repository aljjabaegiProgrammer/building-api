package com.geonlee.api.domin.member.specification;

import com.geonlee.api.entity.Member;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author GEONLEE
 * @since 2024-08-25
 */
public class MemberSpecification {

    public static Specification<Member> memberId(String memberId) {
        return (root, query, criteriaBuilder) ->
                (StringUtils.isEmpty(memberId)) ? criteriaBuilder.conjunction()
                                                : criteriaBuilder.equal(root.get("memberId"), memberId);

    }

    public static Specification<Member> memberName(String memberName) {
        return (root, query, criteriaBuilder) ->
                (StringUtils.isEmpty(memberName)) ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("memberName"), memberName);
    }
}
