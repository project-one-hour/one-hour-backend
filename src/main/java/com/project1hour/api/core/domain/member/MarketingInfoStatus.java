package com.project1hour.api.core.domain.member;

public enum MarketingInfoStatus {
    ALLOW, NOT_ALLOW;

    public static MarketingInfoStatus selectStatus(final boolean allowingMarketingInfo) {
        if (allowingMarketingInfo) {
            return ALLOW;
        }
        return NOT_ALLOW;
    }
}
