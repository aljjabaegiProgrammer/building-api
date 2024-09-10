package com.geonlee.api.config.jwt;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.response.util.ResponseUtils;
import com.geonlee.api.config.jwt.dto.TokenValidDto;
import com.geonlee.api.config.security.SecurityConfig;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-09-06
 */
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    private final List<String> ignoreUris = List.of(SecurityConfig.ignoreUris);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI().replace(this.tokenProvider.contextPath, "");
        if (!this.ignoreUris.contains(requestURI) && !requestURI.startsWith("/swagger-") && !requestURI.startsWith("/api-docs")) {
            log.info("Request URI : " + requestURI);
            //1. SessionsCookie 에서 Access Token 추출
            String accessToken = tokenProvider.getTokenFromCookie(httpServletRequest);
            TokenValidDto tokenValidDto = new TokenValidDto(false, null, accessToken);

            //2. Token 유효성 검증
            if (StringUtils.isNotEmpty(accessToken)) {
                try {
                    checkTokenValidity(tokenValidDto);
                } catch (ExpiredJwtException e) {
                    // Token 만료 시 바로 Token 만료 메시지 전송
                    ResponseUtils.sendResponse(httpServletResponse, ErrorCode.EXPIRED_TOKEN);
                    return;
                }
            }

            //3. Spring Security Authentication token 추가
            if (tokenValidDto.isValid()) {
                log.info(tokenValidDto.getUserId() + "'s token is valid.");
                Collection<SimpleGrantedAuthority> authorities =
                        Arrays.stream(this.tokenProvider
                                        .getClaim(tokenValidDto.getAccessToken(), this.tokenProvider.authClaimName).split(","))
                                .map(SimpleGrantedAuthority::new).toList();
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        new User(this.tokenProvider.getUserId(tokenValidDto.getAccessToken()), "", authorities),
                        tokenValidDto.getAccessToken(),
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private void checkTokenValidity(TokenValidDto tokenValidDto) throws ExpiredJwtException {
        if (StringUtils.isNotEmpty(tokenValidDto.getAccessToken())
                && tokenProvider.validateToken(tokenValidDto.getAccessToken())) {
            String userId = tokenProvider.getUserId(tokenValidDto.getAccessToken());
            tokenValidDto.setValid(true);
            tokenValidDto.setUserId(userId);
        }
    }
}
