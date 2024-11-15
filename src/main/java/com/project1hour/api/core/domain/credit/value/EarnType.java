package com.project1hour.api.core.domain.credit.value;

import lombok.Getter;

@Getter
public enum EarnType {
    WELCOME_REWARD(6);

    private final int creditVolume;

    EarnType(final int creditVolume) {
        this.creditVolume = creditVolume;
    }
}
