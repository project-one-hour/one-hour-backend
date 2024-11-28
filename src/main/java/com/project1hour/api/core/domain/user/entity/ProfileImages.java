package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.value.ProfileImageType;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import io.jsonwebtoken.lang.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Singular;

public class ProfileImages {

    private static final int MAX_PROFILE_IMAGES_SIZE = 3;

    private static final long REQUIRED_PRIMARY_IMAGE_COUNT = 1;
    private static final long NO_PRIMARY_IMAGE = 0;

    private List<ProfileImage> profileImageList;

    @Builder(toBuilder = true)
    public ProfileImages(@Singular("profileImage") final List<ProfileImage> profileImageList) {
        this.profileImageList = List.copyOf(profileImageList);
        validateProfileImageCount();
        validateSinglePrimaryImage();
    }

    public List<ProfileImage> getProfileImageList() {
        return List.copyOf(profileImageList);
    }

    private void validateProfileImageCount() {
        if (Collections.isEmpty(profileImageList) || profileImageList.size() > MAX_PROFILE_IMAGES_SIZE) {
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
