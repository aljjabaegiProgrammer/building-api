package com.geonlee.api.domin.member;

import com.geonlee.api.common.code.NormalCode;
import com.geonlee.api.common.response.ErrorResponse;
import com.geonlee.api.common.response.ItemResponse;
import com.geonlee.api.common.response.ItemsResponse;
import com.geonlee.api.config.message.MessageConfig;
import com.geonlee.api.domin.member.record.*;
import com.geonlee.api.domin.member.validation.MemberValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@RestController
@RequiredArgsConstructor
@Validated({MemberValidationGroup.User.class})
@Tag(name = "회원 관리", description = "회원에 대한 조회/추가/수정/삭제 기능")
@RequestMapping("v1")
public class MemberController {

    private final MemberService memberService;
    private final MessageConfig messageConfig;

    @GetMapping(value = "/members/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "회원 ID 로 회원 조회", description = """
             ### MemberId 유효성 목록
             - `영문, 숫자`만 조회
             - 사용자의 경우 `최소 5자, 최대 30자`까지 전달 가능, `User group` 의 경우에만 체크
            """,
            parameters = {
                    @Parameter(name = "memberId", description = "Member 를 조회하기 위한 ID를 입력", required = true)
            }, operationId = "API-001-01")
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
    public ResponseEntity<ItemResponse<MemberSearchResponse>> getMemberById(
            @PathVariable("memberId")
            @Pattern(regexp = "^[a-zA-Z0-9]+$")
            @Length(min = 5, max = 30, groups = MemberValidationGroup.User.class) String memberId) {
        return ResponseEntity.ok()
                .body(ItemResponse.<MemberSearchResponse>builder()
                        .status(messageConfig.getCode(NormalCode.SEARCH_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.SEARCH_SUCCESS))
                        .item(memberService.getMemberById(memberId))
                        .build());
    }

    @Operation(summary = "전체 회원 조회", description = """
             ### No Argument
            """, operationId = "API-001-02")
    @GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemsResponse<MemberSearchResponse>> getMembers() {
        return ResponseEntity.ok()
                .body(ItemsResponse.<MemberSearchResponse>builder()
                        .status(messageConfig.getCode(NormalCode.SEARCH_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.SEARCH_SUCCESS))
                        .items(memberService.getMembers())
                        .build());
    }

    @PostMapping(value = "/member"
            , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "회원 추가", description = """
             ### 유효성 목록
             - `User group` 의 경우에만 체크
             - 필수 필드 -> memberId
             - 이름은 영문/한글 만 가능
            """, operationId = "API-001-03")
    public ResponseEntity<ItemResponse<MemberCreateResponse>> createMember(@RequestBody
                                                                           @Validated({Default.class, MemberValidationGroup.User.class})
                                                                           @Valid MemberCreateRequest parameter) {
        return ResponseEntity.ok()
                .body(ItemResponse.<MemberCreateResponse>builder()
                        .status(messageConfig.getCode(NormalCode.CREATE_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.CREATE_SUCCESS))
                        .item(memberService.createMember(parameter))
                        .build());
    }

    @PutMapping(value = "/member"
            , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "회원 수정", description = """
             ### 유효성 목록
             - `User group` 의 경우에만 체크
             - 필수 필드 -> memberId
             - 이름은 영문/한글 만 가능
            """, operationId = "API-001-04")
    public ResponseEntity<ItemResponse<MemberModifyResponse>> modifyMember(@RequestBody  @Valid MemberModifyRequest parameter) {
        return ResponseEntity.ok()
                .body(ItemResponse.<MemberModifyResponse>builder()
                        .status(messageConfig.getCode(NormalCode.MODIFY_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.MODIFY_SUCCESS))
                        .item(memberService.modifyMember(parameter))
                        .build());
    }

    @DeleteMapping(value = "/member/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "회원 삭제", description = """
            """,
            parameters = {
                    @Parameter(name = "memberId", description = "Member 를 삭제하기 위한 ID를 입력", required = true)
            }, operationId = "API-001-05")
    public ResponseEntity<ItemResponse<Long>> deleteMember(@PathVariable("memberId") String memberId) {
        return ResponseEntity.ok()
                .body(ItemResponse.<Long>builder()
                        .status(messageConfig.getCode(NormalCode.MODIFY_SUCCESS))
                        .message(messageConfig.getMessage(NormalCode.MODIFY_SUCCESS))
                        .item(memberService.deleteMember(memberId))
                        .build());
    }
}
