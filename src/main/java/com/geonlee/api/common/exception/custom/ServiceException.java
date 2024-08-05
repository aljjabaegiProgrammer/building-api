package com.geonlee.api.common.exception.custom;

import com.geonlee.api.common.code.ErrorCode;

/**
 * @author GEONLEE
 * @since 2024-08-05
 */
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.messageCode());
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, Throwable throwable) {
        super(throwable.getMessage(), throwable);
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() {
        return this.errorCode;
    }
}
