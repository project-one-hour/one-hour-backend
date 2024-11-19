package com.project1hour.api.core.application.credit.export;

public interface WelcomeRewardEventHandler {

    void rewardWelcomeCredit(Payload payload);

    record Payload(Long userId) {
    }
}
