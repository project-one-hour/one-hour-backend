package com.project1hour.api.core.domain.user.value;

public enum NotificationConsent {
    ALLOW, NOT_ALLOW;

    public static NotificationConsent fromBoolean(final boolean allow) {
        return allow ? ALLOW : NOT_ALLOW;
    }

    public boolean isNotificationAllow() {
        return this == ALLOW;
    }
}
