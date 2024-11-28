package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.imports.UserCommandPort;
import com.project1hour.api.core.application.user.imports.UserQueryPort;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.entity.User.UserBuilder;
import com.project1hour.api.core.domain.user.value.InterestId;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Policy : 5개 까지 선택 가능
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserInterestSelectionUseCase {

    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;

    /**
     * Command : 전체 관심사 선택
     */
    protected User selectInterests(final User user, final List<Long> interestIdList) {
        List<InterestId> interestIds = interestIdList.stream().map(InterestId::new).toList();
        if (!userQueryPort.existsAllByInterestIdIn(interestIds)) {
            throw new BadRequestException("알 수 없는 관심사 값이 포함되어 있습니다.", ErrorCode.UNKNOWN_INTERESTS_FOUND);
        }

        UserBuilder userBuilder = user.toBuilder();
        interestIds.forEach(userBuilder::userInterest);
        User userWithSelectedInterests = userBuilder.buildWithAggregation();

        return userCommandPort.saveUserInterestsByUser(userWithSelectedInterests);
    }
}
