package com.project1hour.api.core.infrastructure.mapper;

import static com.project1hour.api.global.support.ValueObjectUtils.nullableValue;

import com.project1hour.api.core.domain.user.entity.UserInterest;
import com.project1hour.api.core.domain.user.value.InterestId;
import com.project1hour.api.core.domain.user.value.UserInterestId;
import com.project1hour.api.core.infrastructure.persistence.UserInterestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInterestMapper {

    public UserInterestEntity toEntity(final UserInterest userInterest) {
        return UserInterestEntity.builder()
                .id(nullableValue(userInterest.getId(), UserInterestId::id))
                .build();
    }

    public UserInterest toDomain(final UserInterestEntity userInterestEntity) {
        return new UserInterest(
                new UserInterestId(userInterestEntity.getId()),
                new InterestId(userInterestEntity.getInterest().getId())
        );
    }
}
