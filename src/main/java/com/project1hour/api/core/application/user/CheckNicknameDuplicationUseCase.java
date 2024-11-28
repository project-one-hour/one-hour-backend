package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.exports.CheckNicknameDuplicationService;
import com.project1hour.api.core.application.user.imports.UserQueryPort;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckNicknameDuplicationUseCase implements CheckNicknameDuplicationService {

    private final UserQueryPort userQueryPort;

    @Override
    public Response checkNickNameDuplication(final CheckNicknameDuplicationService.Request request) {
        try {
            checkIfNicknameDuplicate(request.nickname());
            return new Response(false);
        } catch (Exception e) {
            return new Response(true);
        }
    }

    /**
     * Command : 닉네임 중복 확인
     * TODO : 부적절한 닉네임에 경우 어떻게 처리해야 하나?
     */
    protected void checkIfNicknameDuplicate(final String nickname) {
        boolean isNicknameDuplicate = userQueryPort.existsByUserNickname(new Nickname(nickname));

        if (isNicknameDuplicate) {
            String message = String.format("닉네임이 이미 존재합니다. 현재닉네임 = %s", nickname);
            throw new BadRequestException(message, ErrorCode.DUPLICATED_NICKNAME);
        }
    }
}

