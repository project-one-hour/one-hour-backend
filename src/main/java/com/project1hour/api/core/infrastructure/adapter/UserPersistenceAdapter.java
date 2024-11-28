package com.project1hour.api.core.infrastructure.adapter;

import com.project1hour.api.core.application.user.imports.UserCommandPort;
import com.project1hour.api.core.application.user.imports.UserQueryPort;
import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.application.user.model.UserSocialInfo;
import com.project1hour.api.core.domain.user.entity.ProfileImage;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.entity.UserInterest;
import com.project1hour.api.core.domain.user.value.InterestId;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.SignUpStatus;
import com.project1hour.api.core.domain.user.value.UserId;
import com.project1hour.api.core.infrastructure.mapper.ProfileImageMapper;
import com.project1hour.api.core.infrastructure.mapper.UserInterestMapper;
import com.project1hour.api.core.infrastructure.mapper.UserMapper;
import com.project1hour.api.core.infrastructure.persistence.ImageEntity;
import com.project1hour.api.core.infrastructure.persistence.InterestEntity;
import com.project1hour.api.core.infrastructure.persistence.OauthInfoEntity;
import com.project1hour.api.core.infrastructure.persistence.ProfileImageEntity;
import com.project1hour.api.core.infrastructure.persistence.UserEntity;
import com.project1hour.api.core.infrastructure.persistence.UserInterestEntity;
import com.project1hour.api.core.infrastructure.repository.JpaImageRepository;
import com.project1hour.api.core.infrastructure.repository.JpaInterestRepository;
import com.project1hour.api.core.infrastructure.repository.JpaOauthInfoRepository;
import com.project1hour.api.core.infrastructure.repository.JpaProfileImageRepository;
import com.project1hour.api.core.infrastructure.repository.JpaUserInterestRepository;
import com.project1hour.api.core.infrastructure.repository.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserCommandPort, UserQueryPort {

    private final UserMapper userMapper;
    private final UserInterestMapper userInterestMapper;
    private final ProfileImageMapper profileImageMapper;


    private final JpaUserRepository jpaUserRepository;
    private final JpaInterestRepository jpaInterestRepository;
    private final JpaUserInterestRepository jpaUserInterestRepository;
    private final JpaImageRepository jpaImageRepository;
    private final JpaProfileImageRepository jpaProfileImageRepository;
    private final JpaOauthInfoRepository jpaOauthInfoRepository;

    @Override
    public boolean existsByUserNickname(final Nickname userNickname) {
        return jpaUserRepository.existsByNickname(userNickname.value());
    }

    @Override
    public boolean existsAllByInterestIdIn(final List<InterestId> interestIds) {
        List<Long> interestIdList = interestIds.stream().map(InterestId::id).toList();
        long listSize = interestIdList.size();
        return jpaInterestRepository.existsAllByIdIn(interestIdList, listSize);
    }

    @Override
    public Optional<User> findAuthenticatedUserById(final UserId userId) {
        return jpaUserRepository.findById(userId.id())
                .filter(userEntity -> EnumUtils.isValidEnumIgnoreCase(SignUpStatus.class, userEntity.getSignupStatus()))
                .map(ignored -> User.builder().id(userId).signUpStatus(SignUpStatus.AUTHENTICATED).build());
    }

    @Override
    public User saveUser(final User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedUserEntity = jpaUserRepository.save(userEntity);
        return userMapper.toDomain(savedUserEntity);
    }

    @Override
    public User saveAuthenticatedUser(final User user) {
        UserEntity userEntity = UserEntity.builder().signupStatus(user.getSignUpStatus().name()).build();
        UserEntity savedUserEntity = jpaUserRepository.save(userEntity);
        return User.builder().id(new UserId(savedUserEntity.getId())).signUpStatus(user.getSignUpStatus()).build();
    }

    @Override
    public User saveUserInterestsByUser(final User user) {
        Long userId = user.getId().id();
        UserEntity userEntity = jpaUserRepository.getReferenceById(userId);

        List<UserInterestEntity> userInterestEntityList = user.getUserInterestsToList()
                .stream()
                .map(this::mapToEntityWithInterestEntity)
                .map(userInterestEntity -> userInterestEntity.withUser(userEntity))
                .map(jpaUserInterestRepository::save)
                .toList();

        return userMapper.toDomainWithUserInterests(userEntity, userInterestEntityList);
    }

    @Override
    public User saveProfileImagesByUser(final User user) {
        Long userId = user.getId().id();
        UserEntity userEntity = jpaUserRepository.getReferenceById(userId);

        List<ProfileImageEntity> profileImageEntityList = user.getProfileImagesToList()
                .stream()
                .map(this::mapToEntityWithImageEntity)
                .map(profileImageEntity -> profileImageEntity.withUser(userEntity))
                .map(jpaProfileImageRepository::save)
                .toList();

        return userMapper.toDomainWithProfileImages(userEntity, profileImageEntityList);
    }

    @Override
    public Optional<User> saveUserOauthInfo(final String provider, final UserSocialInfo userSocialInfo,
                                            final TokenPackage tokenPackage) {
        String userSocialId = userSocialInfo.userSocialId();
        OauthInfoEntity oauthInfoEntity = jpaOauthInfoRepository
                .findByProviderAndUserSocialId(provider, userSocialId)
                .map(oauthInfo -> oauthInfo.updateOauthInfo(tokenPackage))
                .orElseGet(() -> OauthInfoEntity.createOauthInfo(provider, userSocialId, tokenPackage));

        return Optional.ofNullable(oauthInfoEntity.getUser()).map(userMapper::toDomain);
    }

    private UserInterestEntity mapToEntityWithInterestEntity(final UserInterest userInterest) {
        InterestEntity interestEntity = jpaInterestRepository.getReferenceById(userInterest.getInterestId().id());
        return userInterestMapper.toEntity(userInterest).withInterest(interestEntity);
    }

    private ProfileImageEntity mapToEntityWithImageEntity(final ProfileImage profileImage) {
        ImageEntity imageEntity = jpaImageRepository.getReferenceById(profileImage.getImageId().id());
        return profileImageMapper.toEntity(profileImage).withImage(imageEntity);
    }
}
