package com.project1hour.api.core.presentation.auth;

import static com.project1hour.api.global.advice.ErrorCode.AUTH_TOKEN_NOT_FOUND;
import static com.project1hour.api.global.advice.ErrorCode.UNAUTHORIZED_PATH;

import com.project1hour.api.core.domain.auth.AuthenticatoinContext;
import com.project1hour.api.core.domain.auth.TokenProvider;
import com.project1hour.api.core.domain.member.Authority;
import com.project1hour.api.global.advice.UnauthorizedException;
import com.project1hour.api.global.support.JwtTokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final AuthenticatoinContext authenticatoinContext;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {

        String token = JwtTokenExtractor.extractToken(request)
                .orElseThrow(
                        () -> new UnauthorizedException("헤더에 토큰 값이 존재하지 않습니다.", AUTH_TOKEN_NOT_FOUND.getErrorCode())
                );

        String subject = tokenProvider.extractSubject(token);
        authenticatoinContext.setPrincipal(subject);

        if (isNotMemberOnlyPath((HandlerMethod) handler)) {
            return true;
        }

        Authority authority = tokenProvider.extractAuthority(token);
        authorize(authority);

        return true;
    }

    private void authorize(final Authority authority) {
        if (!authority.isMember()) {
            throw new UnauthorizedException("회원만 요청할 수 있는 경로(API) 입니다.", UNAUTHORIZED_PATH.getErrorCode());
        }
    }

    private boolean isNotMemberOnlyPath(final HandlerMethod handlerMethod) {
        return !handlerMethod.hasMethodAnnotation(MemberOnly.class);
    }
}
