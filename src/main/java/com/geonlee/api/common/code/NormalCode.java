package com.geonlee.api.common.code;

/**
 * @author GEONLEE
 * @since 2024-07-30
 */
public enum NormalCode implements ResponseCode {
    SEARCH_SUCCESS("OK"),
    CREATE_SUCCESS("OK"),
    MODIFY_SUCCESS("OK"),
    DELETE_SUCCESS("OK");
    private final String code;
    NormalCode(String code) {
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
