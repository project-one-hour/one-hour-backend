package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.service.CheckNickNameDuplicationService;
import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckNicknameDuplicationUseCase implements CheckNickNameDuplicationService {

    private final UserRepository userRepository;

    @Override
    public boolean checkNickNameDuplication(final CheckNickNameDuplicationService.Request request) {
        checkIfNicknameDuplicate(request.nickname());
        return false;
    }

    /**
     * Command : 닉네임 중복 확인
     */
    protected void checkIfNicknameDuplicate(final String nickname) {
        boolean isNicknameDuplicate = userRepository.existsByNickname(nickname);

        if (isNicknameDuplicate) {
            String message = String.format("닉네임이 이미 존재합니다. 현재닉네임 = %s", nickname);
            throw new BadRequestException(message, ErrorCode.DUPLICATED_NICKNAME);
        }
    }
}

