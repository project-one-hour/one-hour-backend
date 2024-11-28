package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.List;
import lombok.Builder;
import lombok.Singular;

public class UserInterests {

    private static final int USER_INTEREST_LIMIT = 5;

    private List<UserInterest> userInterestList;

    @Builder(toBuilder = true)
    public UserInterests(@Singular("userInterest") final List<UserInterest> userInterestList) {
        this.userInterestList = List.copyOf(userInterestList);
        validateUserInterestDuplicates();
        validateUserInterestCount();
    }

    public List<UserInterest> getUserInterestList() {
        return List.copyOf(userInterestList);
    }

    private void validateUserInterestDuplicates() {
        long distinctInterestIdCount = userInterestList.stream().map(UserInterest::getInterestId).distinct().count();
        if (userInterestList.size() != distinctInterestIdCount) {
            String message = String.format("종복 된 값이 %d개 존재합니다.", userInterestList.size() - distinctInterestIdCount);
            throw new BadRequestException(message, ErrorCode.DUPLICATE_INTERESTS_FOUND);
        }
    }

    private void validateUserInterestCount() {
        if (userInterestList.size() != USER_INTEREST_LIMIT) {
            String message = String.format("관심사가 %d개가 아닙니다. size = %d", USER_INTEREST_LIMIT, userInterestList.size());
            throw new BadRequestException(message, ErrorCode.INVALID_INTERESTS_LIMIT);
        }
    }
}
