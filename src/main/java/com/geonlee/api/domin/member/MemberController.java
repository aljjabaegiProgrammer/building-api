package com.geonlee.api.domin.member;

import com.geonlee.api.domin.member.record.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GEONLEE
 * @since 2024-07-25
 */
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberSearchResponse> getMemberById(@PathVariable("memberId") String memberId) {
        return ResponseEntity.ok()
                .body(memberService.getMemberById(memberId));
    }

    @GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MemberSearchResponse>> getMembers() {
        return ResponseEntity.ok()
                .body(memberService.getMembers());
    }

    @PostMapping(value = "/member"
            , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberCreateResponse> createMember(@RequestBody MemberCreateResponse parameter) {
        return ResponseEntity.ok()
                .body(new MemberCreateResponse(null, null, null, null));
    }

    @PutMapping(value = "/member"
            , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberModifyResponse> modifyMember(@RequestBody MemberModifyRequest parameter) {
        return ResponseEntity.ok()
                .body(new MemberModifyResponse(null, null, null));
    }

    @DeleteMapping(value = "/member"
            , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteMember(@RequestBody MemberDeleteRequest parameter) {
        return ResponseEntity.ok()
                .body(1L);
    }
}
