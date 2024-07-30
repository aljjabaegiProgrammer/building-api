package com.geonlee.api.common.exception;

import com.geonlee.api.common.response.ErrorResponse;
import com.geonlee.api.config.message.MessageConfig;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author GEONLEE
 * @since 2024-07-30
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageConfig messageConfig;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.ok()
                .body(ErrorResponse.builder()
                        .status(messageConfig.getMessage("FAIL.CODE"))
                        .message(messageConfig.getMessage("FAIL.NO.DATA.MESSAGE"))
                        .detailMessage(e.getMessage())
                        .build());
    }
}
