package com.project1hour.api.core.application.user;

import com.project1hour.api.core.infrastructure.user.JpaUserRepository;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command : 닉네임 중복 확인
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckNicknameDuplicationUseCase {

    private final JpaUserRepository jpaUserRepository;

    public boolean checkIfNicknameDuplicate(final String nickname) {
        boolean isNicknameDuplicate = jpaUserRepository.existsByNickNameValue(nickname);

        if (isNicknameDuplicate) {
            String message = String.format("닉네임이 이미 존재합니다. 현재닉네임 = %s", nickname);
            throw new BadRequestException(message, ErrorCode.DUPLICATED_NICKNAME);
        }

        return false;
    }
}
