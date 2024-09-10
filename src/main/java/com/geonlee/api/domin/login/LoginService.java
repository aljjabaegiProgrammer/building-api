package com.geonlee.api.domin.login;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.encryption.CryptoService;
import com.geonlee.api.common.exception.custom.ServiceException;
import com.geonlee.api.config.jwt.TokenProvider;
import com.geonlee.api.config.jwt.record.TokenResponse;
import com.geonlee.api.common.encryption.rsa.RsaCryptoService;
import com.geonlee.api.domin.login.record.LoginRequest;
import com.geonlee.api.domin.member.repository.MemberQueryMethodRepository;
import com.geonlee.api.entity.Member;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberQueryMethodRepository memberQueryMethodRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CryptoService rsaCryptoService;

    @Transactional
    public TokenResponse login(HttpServletResponse httpServletResponse, LoginRequest parameter) {
        Member member = this.memberQueryMethodRepository.findById(parameter.memberId())
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_AUTHENTICATION));
        String decodedPassword = this.rsaCryptoService.decrypt(parameter.password());
        if (!this.passwordEncoder.matches(decodedPassword, member.getPassword())) {
            throw new ServiceException(ErrorCode.NOT_AUTHENTICATION);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                parameter.memberId(), decodedPassword);
        try {
            Authentication authentication = this.authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            TokenResponse tokenResponse = this.tokenProvider.createToken(authentication, member);
            this.tokenProvider.setTokenToCookie(httpServletResponse, tokenResponse.accessToken());
            return tokenResponse;
        } catch (BadCredentialsException e) {
            throw new ServiceException(ErrorCode.SERVICE_ERROR);
        }
    }
}
