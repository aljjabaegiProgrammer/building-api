package com.geonlee.api.config.message;

import com.geonlee.api.common.code.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author GEONLEE
 * @since 2024-07-29
 */
@Configuration
@RequiredArgsConstructor
public class MessageConfig {
    private final MessageSource messageSource;

    public String getMessage(ResponseCode responseCode) {
        return messageSource.getMessage(responseCode.messageCode(), null, LocaleContextHolder.getLocale());
    }

    public String getCode(ResponseCode responseCode) {
        return responseCode.code();
    }
}
