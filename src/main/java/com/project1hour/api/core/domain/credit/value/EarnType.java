package com.project1hour.api.core.domain.credit.value;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum EarnType {
    WELCOME_REWARD(6, true);

    private final int creditVolume;
    private final boolean isReward;

    EarnType(final int creditVolume, final boolean isReward) {
        this.creditVolume = creditVolume;
        this.isReward = isReward;
    }

    public static EarnType find(final String value) {
        String upperCaseValue = value.toUpperCase();

        return Arrays.stream(values())
                .filter(earnType -> upperCaseValue.equals(earnType.name()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("일치하는 충전 방식이 없습니다.", ErrorCode.INVALID_EARN_TYPE_VALUE));
    }

    public boolean isRewardType() {
        return isReward;
    }

    public boolean isPaymentType() {
        return !isReward;
    }
}
