package com.project1hour.api.core.presentation.member;

import com.project1hour.api.core.application.member.MemberService;
import com.project1hour.api.core.presentation.auth.Login;
import com.project1hour.api.core.presentation.auth.MemberOnly;
import com.project1hour.api.core.presentation.member.dto.DuplicatedNicknameResponse;
import com.project1hour.api.core.presentation.member.dto.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @MemberOnly
    public ResponseEntity<Void> signup(@Login final Long memberId,
                                       @Valid @RequestBody final SignUpRequest request) {
        memberService.signUp(memberId, request.toNewMemberInfo());
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/duplicate/{nickname}")
    public ResponseEntity<DuplicatedNicknameResponse> isDuplicate(@Valid @PathVariable final String nickname) {
        boolean isDuplicate = memberService.isDuplicate(nickname);
        DuplicatedNicknameResponse response = new DuplicatedNicknameResponse(isDuplicate);
        return ResponseEntity.ok(response);
    }
}
