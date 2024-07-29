package com.geonlee.api.config.locale;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * @author GEONLEE
 * @since 2024-07-29
 */
@Configuration
public class LocaleConfig extends AcceptHeaderLocaleResolver {
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.KOREA);
        return resolver;
    }

    @Override
    @Nonnull
    public Locale resolveLocale(HttpServletRequest request) {
        String acceptLanguage = request.getHeader("Accept-Language");
        return (StringUtils.isEmpty(acceptLanguage)) ? Locale.getDefault() : Locale.forLanguageTag(acceptLanguage);
    }
}
