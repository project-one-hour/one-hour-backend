package com.project1hour.api.core.domain.member;

public enum Authority {
    ADMIN, GUEST, MEMBER;

    public boolean isMember() {
        return this == MEMBER;
    }
}
