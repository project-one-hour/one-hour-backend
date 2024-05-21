package com.project1hour.api.core.infrastructure.interest.jpa;

import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.core.domain.interest.InterestRepository;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreInterestRepository implements InterestRepository {

    private final JpaInterestRepository jpaInterestRepository;

    @Override
    public List<Interest> getAllByName(final List<String> interestsName) {
        List<Interest> interests = jpaInterestRepository.findByNameIn(interestsName);

        if (interests.size() != interestsName.size()) {
            throw new BadRequestException("존재하지 않는 관심사가 포함되어 있습니다.", ErrorCode.INCLUDE_NOT_EXISTS_INTEREST);
        }

        return interests;
    }

    @Override
    public List<Interest> getAll() {
        return jpaInterestRepository.findAll();
    }
}
