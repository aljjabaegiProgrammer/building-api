package com.geonlee.api.config.jwt;


import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.response.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author GEONLEE
 * @since 2024-09-08<br />
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        log.error("403 FORBIDDEN");
        ResponseUtils.sendResponse(response, ErrorCode.FORBIDDEN);
    }
}