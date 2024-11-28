package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.imports.UserQueryPort;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.value.UserId;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoadAuthenticatedUserUseCase {

    private final UserQueryPort userQueryPort;

    protected User loadAuthenticatedUser(final UserId userId) {
        return userQueryPort.findAuthenticatedUserById(userId)
                .orElseThrow(() -> new NotFoundException("인증이 완료된 회원을 찾을 수 없습니다", ErrorCode.MEMBER_NOT_FOUND));
    }
}
