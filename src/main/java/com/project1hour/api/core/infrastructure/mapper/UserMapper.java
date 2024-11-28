package com.project1hour.api.core.infrastructure.mapper;

import static com.project1hour.api.global.support.ValueObjectUtils.nullableValue;

import com.project1hour.api.core.domain.user.entity.ProfileImages;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.entity.UserInterests;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.MarketingConsent;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.NotificationConsent;
import com.project1hour.api.core.domain.user.value.SignUpStatus;
import com.project1hour.api.core.domain.user.value.UserId;
import com.project1hour.api.core.infrastructure.persistence.ProfileImageEntity;
import com.project1hour.api.core.infrastructure.persistence.UserEntity;
import com.project1hour.api.core.infrastructure.persistence.UserInterestEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserInterestMapper userInterestMapper;
    private final ProfileImageMapper profileImageMapper;

    public UserEntity toEntity(final User user) {
        return UserEntity.builder()
                .id(nullableValue(user.getId(), UserId::id))
                .nickname(user.getNickname().value())
                .gender(user.getGender().name())
                .birthday(user.getBirthday().value())
                .mbti(user.getMbti().name())
                .marketingConsent(user.getMarketingConsent().isMarketingAllowed())
                .notificationConsent(user.getNotificationConsent().isNotificationAllow())
                .signupStatus(user.getSignUpStatus().name())
                .build();
    }

    public User toDomain(final UserEntity userEntity) {
        return User.builder()
                .id(new UserId(userEntity.getId()))
                .nickname(new Nickname(userEntity.getNickname()))
                .gender(Gender.find(userEntity.getGender()))
                .birthday(new Birthday(userEntity.getBirthday()))
                .mbti(Mbti.find(userEntity.getMbti()))
                .marketingConsent(MarketingConsent.fromBoolean(userEntity.getMarketingConsent()))
                .notificationConsent(NotificationConsent.fromBoolean(userEntity.getNotificationConsent()))
                .signUpStatus(SignUpStatus.valueOf(userEntity.getSignupStatus().toUpperCase()))
                .build();
    }

    public User toDomainWithUserInterests(final UserEntity userEntity,
                                          final List<UserInterestEntity> userInterestEntityList) {
        return toDomain(userEntity).toBuilder()
                .userInterests(userInterestEntityList.stream().map(userInterestMapper::toDomain)
                        .collect(Collectors.collectingAndThen(Collectors.toList(), UserInterests::new)))
                .build();
    }

    public User toDomainWithProfileImages(final UserEntity userEntity,
                                          final List<ProfileImageEntity> profileImageEntityList) {
        return toDomain(userEntity).toBuilder()
                .profileImages(profileImageEntityList.stream().map(profileImageMapper::toDomain)
                        .collect(Collectors.collectingAndThen(Collectors.toList(), ProfileImages::new)))
                .build();
    }

    public User toDomain(final UserEntity userEntity, final List<UserInterestEntity> userInterestEntityList,
                         final List<ProfileImageEntity> profileImageEntityList) {
        return toDomain(userEntity).toBuilder()
                .userInterests(userInterestEntityList.stream().map(userInterestMapper::toDomain)
                        .collect(Collectors.collectingAndThen(Collectors.toList(), UserInterests::new)))
                .profileImages(profileImageEntityList.stream().map(profileImageMapper::toDomain)
                        .collect(Collectors.collectingAndThen(Collectors.toList(), ProfileImages::new)))
                .build();
    }
}
