package com.geonlee.api.common.code;

/**
 * @author GEONLEE
 * @since 2024-07-30
 */
public enum ErrorCode implements ResponseCode {
    SERVICE_ERROR("ERR_SV_01"),
    DATA_PROCESSING_ERROR("ERR_SV_02"),
    INVALID_PARAMETER("ERR_CE_01"),
    NO_DATA("ERR_DT_01");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String messageCode() {
        return this.name();
    }
}
