package com.geonlee.api.config.jwt;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.response.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author GEONLEE
 * @since 2024-09-08<br />
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        log.error("401 UNAUTHORIZED");
        ResponseUtils.sendResponse(response, ErrorCode.NOT_AUTHENTICATION);
    }
}