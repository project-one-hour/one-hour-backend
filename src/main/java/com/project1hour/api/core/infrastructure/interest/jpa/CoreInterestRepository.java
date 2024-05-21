package com.project1hour.api.core.infrastructure.interest.jpa;

import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.core.domain.interest.InterestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreInterestRepository implements InterestRepository {

    private final JpaInterestRepository jpaInterestRepository;

    @Override
    public List<Interest> getAllByName(final List<String> interestsName) {
        return jpaInterestRepository.findByNameIn(interestsName);
    }

    @Override
    public List<Interest> getAll() {
        return jpaInterestRepository.findAll();
    }
}
