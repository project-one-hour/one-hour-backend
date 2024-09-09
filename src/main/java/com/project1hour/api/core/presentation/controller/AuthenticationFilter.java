package com.project1hour.api.core.presentation.controller;

import static com.project1hour.api.global.advice.ErrorCode.AUTH_TOKEN_NOT_FOUND;

import com.project1hour.api.core.application.user.manager.TokenAuthManager;
import com.project1hour.api.core.application.user.manager.TokenAuthManager.UserDetail;
import com.project1hour.api.core.presentation.auth.MemberOnly;
import com.project1hour.api.global.advice.UnauthorizedException;
import com.project1hour.api.global.support.JwtTokenExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends AnnotatedUrlMappingFilter<MemberOnly> {

    public static final String AUTHENTICATED_USER = "authenticatedUser";

    private final TokenAuthManager tokenAuthManager;

    @Override
    protected void doProcessFilter(final HttpServletRequest request, final HttpServletResponse response,
                                   final FilterChain filterChain) throws ServletException, IOException {
        String token = JwtTokenExtractor.extractToken(request)
                .orElseThrow(() -> new UnauthorizedException("헤더에 토큰 값이 존재하지 않습니다.", AUTH_TOKEN_NOT_FOUND));

        if (tokenAuthManager.isAuthenticated(token)) {
            UserDetail userDetail = tokenAuthManager.getUserDetail(token);
            request.setAttribute(AUTHENTICATED_USER, userDetail);
        }

        filterChain.doFilter(request, response);
    }
}
