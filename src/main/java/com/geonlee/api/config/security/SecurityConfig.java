package com.geonlee.api.config.security;

import com.geonlee.api.config.jwt.JwtAccessDeniedHandler;
import com.geonlee.api.config.jwt.JwtAuthenticationEntryPoint;
import com.geonlee.api.config.jwt.JwtFilter;
import com.geonlee.api.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GEONLEE
 * @since 2024-09-06
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String[] ignoreUris = {"/v1/login", "/v1/key/rsa", "/v1/key/aes"};
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final String[] swaggerUris = {"swagger-ui.html", "/swagger-ui/**", "/api-docs/**"};
    @Value("${pdkdf2.key}")
    private String pdkdf2Key;

    @Value("${pdkdf2.salt-length}")
    private Integer saltLength;

    @Value("${pdkdf2.iterations}")
    private Integer iterations;

    @Value("${pdkdf2.algorithm}")
    private String algorithm;


    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("SHA-256", new Pbkdf2PasswordEncoder(this.pdkdf2Key, this.saltLength, this.iterations
                , Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.valueOf(this.algorithm)));
        return new DelegatingPasswordEncoder("SHA-256", encoders);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(ignoreUris).permitAll()
                            .requestMatchers(this.swaggerUris).permitAll()
//                            .requestMatchers("/v1/member/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .exceptionHandling(c ->
                        c.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(this.jwtAccessDeniedHandler))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}