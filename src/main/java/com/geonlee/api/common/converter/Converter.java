package com.geonlee.api.common.converter;

import io.micrometer.common.util.StringUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author GEONLEE
 * @since 2024-08-08
 */
public class Converter {
    public static String localDateTimeToFormattedString(LocalDateTime localDateTime) {
        if (ObjectUtils.isEmpty(localDateTime)) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA));
    }

    public static String toUpperCase(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        return text.toUpperCase();
    }
}
