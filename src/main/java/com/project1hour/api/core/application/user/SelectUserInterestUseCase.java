package com.project1hour.api.core.application.user;

import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.User.UserBuilder;
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
public class SelectUserInterestUseCase {

    private final UserRepository userRepository;

    /**
     * Command : 전체 관심사 선택
     */
    protected UserBuilder selectInterestIds(final UserBuilder userBuilder, final List<Long> interestIds) {
        long distinctInterestIdCount = interestIds.stream().distinct().count();
        if (interestIds.size() != distinctInterestIdCount) {
            String message = String.format("종복 된 값이 %d개 존재합니다.", interestIds.size() - distinctInterestIdCount);
            throw new BadRequestException(message, ErrorCode.DUPLICATE_INTERESTS_FOUND);
        }

        if (userRepository.hasMissingInterestIds(interestIds)) {
            throw new BadRequestException("알 수 없는 관심사 값이 포함되어 있습니다.", ErrorCode.UNKNOWN_INTERESTS_FOUND);
        }

        interestIds.forEach(userBuilder::userInterest);

        return userBuilder;
    }
}
