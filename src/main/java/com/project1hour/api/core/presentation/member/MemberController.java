package com.project1hour.api.core.presentation.member;

import com.project1hour.api.core.application.member.MemberService;
import com.project1hour.api.core.presentation.auth.Login;
import com.project1hour.api.core.presentation.auth.MemberOnly;
import com.project1hour.api.core.presentation.member.dto.DuplicatedNicknameResponse;
import com.project1hour.api.core.presentation.member.dto.ProfileImagesResponse;
import com.project1hour.api.core.presentation.member.dto.SignUpRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/duplicate/{nickname}")
    public ResponseEntity<DuplicatedNicknameResponse> isDuplicate(@Valid @PathVariable final String nickname) {
        boolean isDuplicate = memberService.isDuplicate(nickname);
        DuplicatedNicknameResponse response = new DuplicatedNicknameResponse(isDuplicate);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    @MemberOnly
    public ResponseEntity<Void> signup(@Login final Long memberId,
                                       @Valid @RequestBody final SignUpRequest request) {
        memberService.signUp(memberId, request.toNewMemberInfo());

        return ResponseEntity.ok()
                .build();
    }

    @PatchMapping("/upload-profile-images")
    @MemberOnly
    public ResponseEntity<ProfileImagesResponse> uploadProfileImages(@Login final Long memberId,
                                                                     @RequestPart final List<MultipartFile> images) {
        List<String> imageUrls = memberService.uploadProfileImages(memberId, images);
        ProfileImagesResponse response = new ProfileImagesResponse(imageUrls);

        return ResponseEntity.ok(response);
    }
}
