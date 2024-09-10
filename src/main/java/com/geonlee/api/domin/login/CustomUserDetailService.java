package com.geonlee.api.domin.login;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.exception.custom.ServiceException;
import com.geonlee.api.domin.member.repository.MemberQueryMethodRepository;
import com.geonlee.api.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * AuthenticationManagerBuilder 에 의해 호출되며 인증정보를 포함한<br />
 * Security UserDetail 정보를 리턴한다.
 *
 * @author GEONLEE
 * @since 2024-09-06
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final MemberQueryMethodRepository memberQueryMethodRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberQueryMethodRepository.findById(username).map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> not found."));
    }

    private User createUser(String username, Member member) {
        if (member.getAuthority() == null) {
            throw new ServiceException(ErrorCode.FORBIDDEN);
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(member.getAuthority().getAuthorityCode()));
        log.info(username + "'s Authority : {}", grantedAuthorities);
        return new User(
                member.getMemberId(),
                member.getPassword(),
                grantedAuthorities
        );
    }
}
