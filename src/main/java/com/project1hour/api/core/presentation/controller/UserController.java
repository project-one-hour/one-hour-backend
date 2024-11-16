package com.project1hour.api.core.presentation.controller;

import static com.project1hour.api.core.presentation.filter.AuthenticationFilter.AUTHENTICATED_USER;

import com.project1hour.api.core.application.user.exports.CheckNicknameDuplicationService;
import com.project1hour.api.core.application.user.exports.OauthLoginService;
import com.project1hour.api.core.application.user.exports.UserRegistrationService;
import com.project1hour.api.core.application.user.model.UserDetail;
import com.project1hour.api.core.presentation.dto.UserRegistrationRequest;
import com.project1hour.api.core.presentation.filter.ImageOptimize;
import com.project1hour.api.core.presentation.filter.MemberOnly;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final OauthLoginService oauthLoginService;

    @GetMapping("/duplicate/{nickname}")
    public ResponseEntity<CheckNicknameDuplicationService.Response> isDuplicated(@PathVariable final String nickname) {
        var request = new CheckNicknameDuplicationService.Request(nickname);
        var response = checkNicknameDuplicationService.checkNickNameDuplication(request);
        return ResponseEntity.ok(response);
    }

    @MemberOnly
    @ImageOptimize
    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> signUp(@RequestAttribute(AUTHENTICATED_USER) final UserDetail userDetail,
                                       @RequestPart("profile") final UserRegistrationRequest request,
                                       @RequestPart("primaryImage") final MultipartFile primaryImage,
                                       @RequestPart(name = "secondaryImages", required = false) final List<MultipartFile> secondaryImages) {
        var requestWithMultipart = request
                .withPrimaryImage(primaryImage)
                .withSecondaryImages(secondaryImages)
                .withUserId(userDetail.userId());
        userRegistrationService.signUpUser(requestWithMultipart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/auth-callback/{provider}")
    public ResponseEntity<OauthLoginService.Response> socialLoginCallback(@PathVariable final String provider,
                                                                          @RequestParam("code") final String authorizationCode) {
        var request = new OauthLoginService.Request(provider, authorizationCode);
        var response = oauthLoginService.login(request);
        return ResponseEntity.ok(response);
    }
}
