package com.geonlee.api.domin.login;

import com.geonlee.api.common.code.NormalCode;
import com.geonlee.api.common.response.ItemResponse;
import com.geonlee.api.config.jwt.record.TokenResponse;
import com.geonlee.api.config.message.MessageConfig;
import com.geonlee.api.domin.login.record.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "로그인", description = "로그인 요청")
@RequestMapping("v1")
public class LoginController {

    private final LoginService loginService;
    private final MessageConfig messageConfig;

    @Operation(summary = "로그인 요청", description = """
            """, operationId = "API-000-01")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemResponse<TokenResponse>> getMemberById(@RequestBody @Valid LoginRequest parameter,
                                                                     HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok()
                .body(ItemResponse.<TokenResponse>builder()
                        .status(messageConfig.getCode(NormalCode.SEARCH_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.SEARCH_SUCCESS))
                        .item(loginService.login(httpServletResponse, parameter))
                        .build());
    }
}
