package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.AbstractDomainEntity;
import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.user.value.ProfileImageId;
import com.project1hour.api.core.domain.user.value.ProfileImageType;
import lombok.Getter;

@Getter
public class ProfileImage extends AbstractDomainEntity<ProfileImageId> {

    private final ProfileImageId id;

    private ImageId imageId;

    private ProfileImageType profileImageType;

    public ProfileImage(final ProfileImageId id, final ImageId imageId, final ProfileImageType profileImageType) {
        this.id = id;
        this.imageId = imageId;
        this.profileImageType = profileImageType;
    }

    public static ProfileImage createNewProfileImage(final ImageId imageId, final ProfileImageType profileImageType) {
        return new ProfileImage(null, imageId, profileImageType);
    }
}
