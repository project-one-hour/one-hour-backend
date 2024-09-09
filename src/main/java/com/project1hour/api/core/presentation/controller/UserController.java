package com.project1hour.api.core.presentation.controller;

import com.project1hour.api.core.application.user.service.CheckNicknameDuplicationService;
import com.project1hour.api.core.presentation.auth.MemberOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * TODO : 바뀐 Validation 스펙 테스트 -> Spring Aop기반 검증 (@Validated)가 없으면 메서드 파라미터 검증이 가능함
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CheckNicknameDuplicationService checkNicknameDuplicationService;

    @GetMapping("/duplicate/{nickname}")
    @MemberOnly
    public ResponseEntity<CheckNicknameDuplicationService.Response> isDuplicated(@PathVariable final String nickname) {
        var request = new CheckNicknameDuplicationService.Request(nickname);
        var response = checkNicknameDuplicationService.checkNickNameDuplication(request);
        return ResponseEntity.ok(response);
    }
}
