package com.project1hour.api.core.presentation.observer;

import com.project1hour.api.core.application.credit.exports.WelcomeRewardEventHandler;
import com.project1hour.api.core.application.credit.exports.WelcomeRewardEventHandler.Payload;
import com.project1hour.api.core.application.user.model.event.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditEventListener {

    private final WelcomeRewardEventHandler welcomeRewardEventHandler;

    @EventListener(classes = UserUpdatedEvent.class, condition = "#event.isNew()")
    public void rewardWelcomeCreditForNewUser(final UserUpdatedEvent event) {
        Payload payload = new Payload(event.userId());
        welcomeRewardEventHandler.rewardWelcomeCredit(payload);
    }
}
