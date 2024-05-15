package com.project1hour.api.core.presentation.auth;

import com.project1hour.api.core.application.auth.AuthService;
import com.project1hour.api.core.implement.auth.dto.TokenResponse;
import com.project1hour.api.core.presentation.auth.dto.OauthAccessTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/{provider}")
    public ResponseEntity<TokenResponse> login(@PathVariable final String provider,
                                               @Valid @RequestBody final OauthAccessTokenRequest request) {
        TokenResponse response = authService.createToken(provider, request.token());

        return ResponseEntity.ok(response);
    }
}
