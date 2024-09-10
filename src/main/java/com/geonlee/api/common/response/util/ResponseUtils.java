package com.geonlee.api.common.response.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geonlee.api.common.code.ResponseCode;
import com.geonlee.api.common.contextHolder.ApplicationContextHolder;
import com.geonlee.api.common.response.ErrorResponse;
import com.geonlee.api.config.message.MessageConfig;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author GEONLEE
 * @since 2024-09-09
 */
@Slf4j
public class ResponseUtils {

    public static void sendResponse(ServletResponse response, ResponseCode errorCode) {
        MessageConfig messageConfig = ApplicationContextHolder.getContext().getBean(MessageConfig.class);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(ErrorResponse.builder()
                    .status(errorCode.code())
                    .message(messageConfig.getMessage(errorCode))
                    .build()));
        } catch (IOException e) {
            log.error("Fail to send response.");
        }
    }
}
