package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.api.UserInterestClient;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import io.jsonwebtoken.lang.Collections;
import java.util.List;
import java.util.Set;
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

    private final UserInterestClient userInterestClient;

    /**
     * Command : 전체 관심사 선택
     */
    protected Set<Long> selectInterestIds(final List<Long> interestIds) {
        Set<Long> interestIdSet = Collections.asSet(interestIds);

        if (interestIdSet.size() != interestIds.size()) {
            String message = String.format("종복 된 값이 %d개 존재합니다.", interestIds.size() - interestIdSet.size());
            throw new BadRequestException(message, ErrorCode.DUPLICATE_INTERESTS_FOUND);
        }

        if (userInterestClient.hasMissingInterestIds(interestIdSet)) {
            throw new BadRequestException("알 수 없는 관심사 값이 포함되어 있습니다.", ErrorCode.UNKNOWN_INTERESTS_FOUND);
        }

        return interestIdSet;
    }
}
