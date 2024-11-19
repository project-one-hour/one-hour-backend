package com.project1hour.api.core.presentation.observer;

import com.project1hour.api.core.application.credit.export.WelcomeRewardEventHandler;
import com.project1hour.api.core.application.credit.export.WelcomeRewardEventHandler.Payload;
import com.project1hour.api.core.application.user.model.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditEventListener {

    private final WelcomeRewardEventHandler welcomeRewardEventHandler;

    @EventListener(UserRegisteredEvent.class)
    public void rewardWelcomeCreditForNewUser(final UserRegisteredEvent event) {
        Payload payload = new Payload(event.newUserId());
        welcomeRewardEventHandler.rewardWelcomeCredit(payload);
    }
}
