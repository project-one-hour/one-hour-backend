package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterests {

    private static final int USER_INTEREST_LIMIT = 5;

    @Getter(AccessLevel.PACKAGE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInterest> userInterestList;

    public UserInterests(final List<UserInterest> userInterests) {
        this.userInterestList = List.copyOf(userInterests);
        validate();
    }

    private void validate() {
        if (userInterestList.size() != USER_INTEREST_LIMIT) {
            String message = String.format("관심사가 %d개가 아닙니다. size = %d", USER_INTEREST_LIMIT, userInterestList.size());
            throw new BadRequestException(message, ErrorCode.INVALID_INTERESTS_LIMIT);
        }
    }
}
