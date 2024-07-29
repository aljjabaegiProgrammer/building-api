package com.geonlee.api.config.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

/**
 * @author GEONLEE
 * @since 2024-07-29
 */
@Configuration
@RequiredArgsConstructor
public class MessageConfig {
    private final MessageSource messageSource;

    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    public String getCode(String key) {
        return messageSource.getMessage(key, null, Locale.KOREA);
    }
}
