package com.project1hour.api.core.infrastructure.mapper;

import static com.project1hour.api.global.support.ValueObjectUtils.nullableValue;

import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.user.entity.ProfileImage;
import com.project1hour.api.core.domain.user.value.ProfileImageId;
import com.project1hour.api.core.domain.user.value.ProfileImageType;
import com.project1hour.api.core.infrastructure.persistence.ProfileImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileImageMapper {

    public ProfileImageEntity toEntity(final ProfileImage profileImage) {
        return ProfileImageEntity.builder()
                .id(nullableValue(profileImage.getId(), ProfileImageId::id))
                .profileImageType(profileImage.getProfileImageType().name())
                .build();
    }

    public ProfileImage toDomain(final ProfileImageEntity profileImageEntity) {
        return new ProfileImage(
                new ProfileImageId(profileImageEntity.getId()),
                new ImageId(profileImageEntity.getImage().getId()),
                ProfileImageType.valueOf(profileImageEntity.getProfileImageType().toUpperCase())
        );
    }
}
