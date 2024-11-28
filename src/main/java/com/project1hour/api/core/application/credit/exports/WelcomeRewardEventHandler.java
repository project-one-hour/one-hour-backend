package com.project1hour.api.core.application.credit.exports;

import com.project1hour.api.core.domain.user.value.UserId;

public interface WelcomeRewardEventHandler {

    void rewardWelcomeCredit(Payload payload);

    record Payload(UserId userId) {
    }
}
