package com.geonlee.api.domin.authority;

import com.geonlee.api.common.code.NormalCode;
import com.geonlee.api.common.response.ErrorResponse;
import com.geonlee.api.common.response.ItemResponse;
import com.geonlee.api.common.response.ItemsResponse;
import com.geonlee.api.config.message.MessageConfig;
import com.geonlee.api.domin.authority.record.AuthoritySearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GEONLEE
 * @since 2024-08-19
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "권한 관리", description = "권한에 대한 조회 기능")
@RequestMapping("v1")
public class AuthorityController {

    private final MessageConfig messageConfig;
    private final AuthorityService authorityService;

    @GetMapping(value = "/authorities/{authorityCd}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "권한 코드로 회원 조회", description = """
             ### authorityCd 유효성 목록
             - `영문, 특수문자('_')`만 가능
             - `최소 5자, 최대 20자`까지 전달 가능
            """,
            parameters = {
                    @Parameter(name = "authorityCd", description = "권한을 조회하기 위한 권한 코드를 입력", required = true)
            }, operationId = "API-002-01")
    @ApiResponses({
            @ApiResponse(responseCode = "OK", description = "정상 응답", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "ERR_DT_01", description = "데이터가 존재하지 않음"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)
                    , examples = @ExampleObject(value = """
                            {
                                "status": "ERR_DT_01",
                                "message": "데이터가 존재하지 않습니다."
                            }
                    """)
            )
            )
    })
    public ResponseEntity<ItemResponse<AuthoritySearchResponse>> getAuthorityById(
            @PathVariable("authorityCd")
            @Pattern(regexp = "^[A-Z_]+$")
            @Length(min = 5, max = 20) String authorityCd) {
        return ResponseEntity.ok()
                .body(ItemResponse.<AuthoritySearchResponse>builder()
                        .status(messageConfig.getCode(NormalCode.SEARCH_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.SEARCH_SUCCESS))
                        .item(authorityService.getAuthorityById(authorityCd))
                        .build());
    }

    @Operation(summary = "전체 회원 조회", description = """
             ### No Argument
            """, operationId = "API-002-02")
    @GetMapping(value = "/authorities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemsResponse<AuthoritySearchResponse>> getAuthorities() {
        return ResponseEntity.ok()
                .body(ItemsResponse.<AuthoritySearchResponse>builder()
                        .status(messageConfig.getCode(NormalCode.SEARCH_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.SEARCH_SUCCESS))
                        .items(authorityService.getAuthorities())
                        .build());
    }
}
