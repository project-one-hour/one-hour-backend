package com.project1hour.api.core.implement.interest;

import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.core.domain.interest.InterestRepository;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestReader {

    private final InterestRepository interestRepository;

    public List<Interest> readAllWithNames(final List<String> interestNames) {
        List<Interest> interests = interestRepository.getAllByName(interestNames);

        if (interests.size() != interestNames.size()) {
            throw new BadRequestException("존재하지 않는 관심사가 포함되어 있습니다.", ErrorCode.INCLUDE_NOT_EXISTS_INTEREST);
        }
        return interests;
    }

    public List<Interest> readAll() {
        return interestRepository.getAll();
    }
}
