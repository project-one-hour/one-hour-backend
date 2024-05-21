package com.project1hour.api.core.implement.interest;

import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.core.domain.interest.InterestRepository;
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
        return interestRepository.getAllByName(interestNames);
    }

    public List<Interest> readAll() {
        return interestRepository.getAll();
    }
}
