package com.geonlee.api.config.jwt;

import com.geonlee.api.config.jwt.record.TokenResponse;
import com.geonlee.api.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author GEONLEE
 * @since 2024-09-06
 */
@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    public final String authClaimName = "AUTH";
    @Value("${server.servlet.context-path}")
    public String contextPath;
    @Value("${jwt.auth-key}")
    private String jwtAuthKey;
    @Value("${jwt.cookie-token-name}")
    private String tokenNameInCookie;
    private SecretKey signingKey;

    @Override
    public void afterPropertiesSet() {
        this.signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(this.jwtAuthKey));
    }

    public TokenResponse createToken(Authentication authentication, Member member) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date now = new Date();
        long dateTime = (now).getTime();
        //1시간
        long tokenValidityInMilliseconds = 1000 * 3600;
        Date accessValidity = new Date(dateTime + tokenValidityInMilliseconds);
        //24시간
        long refreshTokenValidityInMilliseconds = 24 * (1000 * 3600);
        Date refreshValidity = new Date(dateTime + refreshTokenValidityInMilliseconds);
        return TokenResponse.builder()
                .accessToken(
                        Jwts.builder()
                                .subject(authentication.getName())
                                .claim(this.authClaimName, authorities)
                                .issuedAt(now)
                                .expiration(accessValidity)
                                .signWith(this.signingKey)
                                .compact())
                .refreshToken(
                        Jwts.builder()
                                .subject(authentication.getName())
                                .claim(this.authClaimName, authorities)
                                .issuedAt(now)
                                .expiration(refreshValidity)
                                .signWith(this.signingKey)
                                .compact())
                .build();
    }

    public String getTokenFromCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        String requestURI = httpServletRequest.getRequestURI().replace(this.contextPath, "");
        if (cookies == null) {
            log.error("No cookies found in request. Request URI: {}", requestURI);
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> this.tokenNameInCookie.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .map(this::doXssFilter)
                .orElseGet(() -> {
                    log.error("Access token in cookie does not exist. Request URI: {}", requestURI);
                    return null;
                });
    }

    public void setTokenToCookie(HttpServletResponse httpServletResponse, String accessToken) {
        log.info("Add Token in Cookie.");
        Cookie cookie = new Cookie(this.tokenNameInCookie, accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    public String getUserId(String token) {
        return Jwts.parser().verifyWith(this.signingKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String getClaim(String token, String claimName) {
        return (String) Jwts.parser().verifyWith(this.signingKey).build().parseSignedClaims(token).getPayload().get(claimName);
    }

    public boolean validateToken(String token) throws ExpiredJwtException {
        try {
            Jwts.parser().verifyWith(this.signingKey).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid jwt signature.", e);
        } catch (ExpiredJwtException e) {
            log.error("This token is expired.", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("This jwt token is not supported.", e);
        } catch (IllegalArgumentException e) {
            log.error("Invalid jwt token.", e);
        } catch (DecodingException e) {
            log.error("JWT token decoding failed", e);
        }
        return false;
    }

    /**
     * XSS(Cross-Site Scripting) 공격을 방지하기 위해 특수문자를 HTML 로 변환
     */
    private String doXssFilter(String origin) {
        if (StringUtils.isEmpty(origin)) {
            return null;
        }
        return origin.replace("'", "&#x27;")
                .replace("\"", "&quot;")
                .replace("(", "&#40;")
                .replace(")", "&#41;")
                .replace("/", "&#x2F;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&", "&amp;");
    }
}
