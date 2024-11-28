package com.project1hour.api.core.domain.user.value;

public enum MarketingConsent {
    ALLOW, NOT_ALLOW;

    public static MarketingConsent fromBoolean(final boolean allow) {
        return allow ? ALLOW : NOT_ALLOW;
    }

    public boolean isMarketingAllowed() {
        return this == ALLOW;
    }
}
