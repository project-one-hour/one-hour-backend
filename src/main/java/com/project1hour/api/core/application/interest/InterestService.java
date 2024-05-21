package com.project1hour.api.core.application.interest;

import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.core.implement.interest.InterestReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestReader interestReader;

    public List<Interest> getAll() {
        return interestReader.readAll();
    }
}
