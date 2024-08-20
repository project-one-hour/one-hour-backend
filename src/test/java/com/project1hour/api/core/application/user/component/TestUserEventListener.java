package com.project1hour.api.core.application.user.component;

import com.project1hour.api.core.domain.user.event.UserDomainEvent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestUserEventListener {

    private final List<UserDomainEvent> events = new ArrayList<>();

    @EventListener
    public void onEvent(UserDomainEvent event) {
        events.add(event);
    }

    public void reset() {
        events.clear();
    }

    public List<UserDomainEvent> getEvents() {
        return events;
    }
}
