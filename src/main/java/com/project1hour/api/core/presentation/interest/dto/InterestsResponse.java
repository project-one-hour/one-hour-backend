package com.project1hour.api.core.presentation.interest.dto;

import com.project1hour.api.core.domain.interest.Interest;
import java.util.List;

public record InterestsResponse(List<String> interestNames) {

    public static InterestsResponse createResponse(final List<Interest> interests) {
        return new InterestsResponse(interests.stream()
                .map(Interest::getName)
                .toList());
    }
}
