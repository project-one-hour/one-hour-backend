package com.project1hour.api.core.presentation.filter;

import static com.project1hour.api.global.advice.ErrorCode.AUTH_TOKEN_NOT_FOUND;

import com.project1hour.api.core.application.user.exports.TokenAuthenticationFacade;
import com.project1hour.api.core.application.user.model.UserDetail;
import com.project1hour.api.core.presentation.filter.core.AnnotatedUrlMappingFilter;
import com.project1hour.api.global.advice.UnauthorizedException;
import com.project1hour.api.global.support.JwtTokenExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends AnnotatedUrlMappingFilter<MemberOnly> {

    public static final String AUTHENTICATED_USER = "authenticatedUser";

    private final TokenAuthenticationFacade tokenAuthenticationFacade;

    @Override
    protected void doProcessFilter(final HttpServletRequest request, final HttpServletResponse response,
                                   final FilterChain filterChain) throws ServletException, IOException {
        String token = JwtTokenExtractor.extractToken(request)
                .orElseThrow(() -> new UnauthorizedException("헤더에 토큰 값이 존재하지 않습니다.", AUTH_TOKEN_NOT_FOUND));

        UserDetail userDetail = tokenAuthenticationFacade.authenticateUser(token);
        request.setAttribute(AUTHENTICATED_USER, userDetail);

        filterChain.doFilter(request, response);
    }
}
