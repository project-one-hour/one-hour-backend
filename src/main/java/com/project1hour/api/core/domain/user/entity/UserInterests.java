package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterests {

    private static final int USER_INTEREST_LIMIT = 5;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserInterest> userInterestList;

    @Builder
    public UserInterests(final Set<Long> interestIds, final User user) {
        validate(interestIds);
        this.userInterestList = createUserInterestList(interestIds, user);
    }

    private void validate(final Set<Long> interestIds) {
        if (CollectionUtils.isEmpty(interestIds)) {
            throw new BadRequestException("관심사가 비어있습니다.", ErrorCode.INTERESTS_NOT_PROVIDED);
        }

        if (interestIds.size() != USER_INTEREST_LIMIT) {
            String message = String.format("관심사가 %d개가 아닙니다. size = %d", USER_INTEREST_LIMIT, interestIds.size());
            throw new BadRequestException(message, ErrorCode.INVALID_INTERESTS_LIMIT);
        }
    }

    private Set<UserInterest> createUserInterestList(final Set<Long> interestIds, final User user) {
        return interestIds.stream()
                .map(interestId -> UserInterest.builder()
                        .interestId(interestId)
                        .user(user)
                        .build()
                )
                .collect(Collectors.toSet());
    }
}
