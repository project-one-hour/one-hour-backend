package com.project1hour.api.core.presentation.controller;

import com.project1hour.api.core.application.user.exports.CheckNicknameDuplicationService;
import com.project1hour.api.core.application.user.exports.UserRegistrationService;
import com.project1hour.api.core.presentation.dto.UserRegistrationRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * TODO : 바뀐 Validation 스펙 테스트 -> Spring Aop기반 검증 (@Validated)가 없으면 메서드 파라미터 검증이 가능함
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CheckNicknameDuplicationService checkNicknameDuplicationService;
    private final UserRegistrationService userRegistrationService;

    @GetMapping("/duplicate/{nickname}")
    public ResponseEntity<CheckNicknameDuplicationService.Response> isDuplicated(@PathVariable final String nickname) {
        var request = new CheckNicknameDuplicationService.Request(nickname);
        var response = checkNicknameDuplicationService.checkNickNameDuplication(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserRegistrationService.Response> signUp(
            @RequestPart("userRegistration") UserRegistrationRequest request,
            @RequestPart("primaryImage") MultipartFile primaryImage,
            @RequestPart("secondaryImages") List<MultipartFile> secondaryImages) {

        var requestWithMultipart = request.withPrimaryImage(primaryImage).withSecondaryImages(secondaryImages);
        var response = userRegistrationService.signUpUser(requestWithMultipart);
        return ResponseEntity.ok(response);
    }
    
}
