package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.AbstractDomainEntity;
import com.project1hour.api.core.domain.user.value.InterestId;
import com.project1hour.api.core.domain.user.value.UserInterestId;
import lombok.Getter;

@Getter
public class UserInterest extends AbstractDomainEntity<UserInterestId> {

    private final UserInterestId id;

    private InterestId interestId;

    public UserInterest(final UserInterestId id, final InterestId interestId) {
        this.id = id;
        this.interestId = interestId;
    }

    public static UserInterest createNewUserInterest(final InterestId interestId) {
        return new UserInterest(null, interestId);
    }
}
