package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.value.ProfileImageType;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * TODO : 대표 사진 validate 작성
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImages {

    private static final int MAX_PROFILE_IMAGES_SIZE = 3;

    private static final long REQUIRED_PRIMARY_IMAGE_COUNT = 1;
    private static final long NO_PRIMARY_IMAGE = 0;

    @Getter(AccessLevel.PACKAGE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileImage> profileImageList;

    public ProfileImages(final List<ProfileImage> profileImages) {
        this.profileImageList = profileImages;
        validateProfileImageCount();
        validateSinglePrimaryImage();
    }

    private void validateProfileImageCount() {
        if (profileImageList.isEmpty() || profileImageList.size() > MAX_PROFILE_IMAGES_SIZE) {
            String message = String.format("프로필 이미지는 1장 이상 3장 이하여야 합니다. size = %d", profileImageList.size());
            throw new BadRequestException(message, ErrorCode.INVALID_MEMBER_PROFILE_IMAGE_SIZE);
        }
    }

    private void validateSinglePrimaryImage() {
        long countPrimaryImages = countPrimaryImages();

        if (countPrimaryImages == NO_PRIMARY_IMAGE) {
            throw new BadRequestException("대표 프로필 이미지가 존재하지 않습니다.", ErrorCode.NO_PRIMARY_PROFILE_IMAGE);
        }

        if (countPrimaryImages != REQUIRED_PRIMARY_IMAGE_COUNT) {
            String message = String.format("대표 프로필 이미지는 1장만 있어야 합니다. 현재 개수: %d", countPrimaryImages);
            throw new BadRequestException(message, ErrorCode.TOO_MANY_PRIMARY_PROFILE_IMAGES);
        }
    }

    private long countPrimaryImages() {
        return profileImageList.stream()
                .collect(Collectors.groupingBy(ProfileImage::getProfileImageType, Collectors.counting()))
                .getOrDefault(ProfileImageType.PRIMARY, NO_PRIMARY_IMAGE);
    }
}
