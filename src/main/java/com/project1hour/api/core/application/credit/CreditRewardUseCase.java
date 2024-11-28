package com.project1hour.api.core.application.credit;

import com.project1hour.api.core.application.credit.exports.WelcomeRewardEventHandler;
import com.project1hour.api.core.application.credit.imports.CreditCommandPort;
import com.project1hour.api.core.domain.credit.entity.Credit;
import com.project1hour.api.core.domain.credit.value.EarnType;
import com.project1hour.api.core.domain.user.value.UserId;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditRewardUseCase implements WelcomeRewardEventHandler {

    private final CreditCommandPort creditCommandPort;

    @Override
    public void rewardWelcomeCredit(final Payload payload) {
        rewardCredit(payload.userId(), EarnType.WELCOME_REWARD);
    }

    protected void rewardCredit(final UserId userId, final EarnType rewardEarnType) {
        if (!rewardEarnType.isRewardType()) {
            throw new BadRequestException("리워드 타입의 충전 형식이 아닙니다.", ErrorCode.INVALID_REWARD_TYPE);
        }

        Credit rewardCredit = Credit.createCreditByEarnType(userId, rewardEarnType);
        creditCommandPort.create(rewardCredit);
    }
}
