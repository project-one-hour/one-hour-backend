package com.project1hour.api.core.domain.user.value;

public enum SignUpStatus {
    AUTHENTICATED,
    SIGNED_UP;

    public boolean isAuthenticated() {
        return this == SignUpStatus.AUTHENTICATED;
    }
}
