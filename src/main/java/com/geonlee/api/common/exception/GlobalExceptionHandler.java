package com.geonlee.api.common.exception;

import com.geonlee.api.common.code.ErrorCode;
import com.geonlee.api.common.response.ErrorResponse;
import com.geonlee.api.config.message.MessageConfig;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @author GEONLEE
 * @since 2024-07-30
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageConfig messageConfig;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
        return generateErrorResponse(ErrorCode.NO_DATA, e);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExist(EntityExistsException e) {
        return generateErrorResponse(ErrorCode.EXISTS_DATA, e);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQL(SQLException e) {
        return generateErrorResponse(ErrorCode.EXISTS_DATA, e);
    }

    private ResponseEntity<ErrorResponse> generateErrorResponse(ErrorCode errorCode, Exception e) {
        LOGGER.error(errorCode.messageCode(), e);
        return ResponseEntity.ok()
                .body(ErrorResponse.builder()
                        .status(messageConfig.getCode(errorCode))
                        .message(messageConfig.getMessage(errorCode))
                        .detailMessage(e.getMessage())
                        .build());
    }
}
