package com.project1hour.api.core.infrastructure.user.client;

import static com.project1hour.api.core.domain.user.value.Interest.INTEREST_IDS;

import com.project1hour.api.core.application.user.api.UserInterestClient;
import com.project1hour.api.core.domain.user.value.Interest;
import java.util.Collection;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UserInterestServiceClient implements UserInterestClient {

    /**
     * TODO: 임시 저장소, 추후 service 인터페이스를 사용한다.
     */
    private final Map<Long, Interest> interestRepository = INTEREST_IDS;

    public boolean hasMissingInterestIds(Collection<Long> interestIds) {
        return interestIds.stream().anyMatch(interestId -> !interestRepository.containsKey(interestId));
    }
}
