package com.project1hour.api.core.presentation.auth;

import com.project1hour.api.core.application.auth.AuthService;
import com.project1hour.api.core.implement.auth.dto.TokenResponse;
import com.project1hour.api.core.presentation.auth.dto.OauthAccessTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/oauth/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody final OauthAccessTokenRequest request) {
        TokenResponse response = authService.createToken(request.provider(), request.token());

        return ResponseEntity.ok(response);
    }
}
