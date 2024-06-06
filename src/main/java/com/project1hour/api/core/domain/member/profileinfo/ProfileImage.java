package com.project1hour.api.core.domain.member.profileinfo;

import static com.project1hour.api.core.domain.member.profileinfo.ProfileImageFiles.MAX_PROFILE_IMAGE_SIZE;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.Embeddable;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

    private static final int FIRST_IMAGE_IDX = 0;
    private static final int SECOND_IMAGE_IDX = 1;
    private static final int THIRD_IMAGE_IDX = 2;
    public static final int MIN_PROFILE_IMAGE_SIZE = 1;

    private String firstProfileImageUrl;
    private String secondProfileImageUrl;
    private String thirdProfileImageUrl;

    @Builder
    public ProfileImage(final String firstProfileImageUrl, final String secondProfileImageUrl,
                        final String thirdProfileImageUrl) {
        this.firstProfileImageUrl = firstProfileImageUrl;
        this.secondProfileImageUrl = secondProfileImageUrl;
        this.thirdProfileImageUrl = thirdProfileImageUrl;
    }

    public static ProfileImage from(final List<String> imageUrls) {
        validateSize(imageUrls);

        ProfileImageBuilder builder = ProfileImage.builder();
        if (imageUrls.size() >= 1) {
            builder.firstProfileImageUrl(imageUrls.get(FIRST_IMAGE_IDX));
        }
        if (imageUrls.size() >= 2) {
            builder.secondProfileImageUrl(imageUrls.get(SECOND_IMAGE_IDX));
        }
        if (imageUrls.size() >= 3) {
            builder.thirdProfileImageUrl(imageUrls.get(THIRD_IMAGE_IDX));
        }

        return builder.build();
    }

    public static void validateSize(final List<String> imageUrls) {
        if (imageUrls.isEmpty() || imageUrls.size() > MAX_PROFILE_IMAGE_SIZE) {
            String message = String.format("프로필 이미지는 1장 이상 3장 이하여야 합니다. size = %d", imageUrls.size());
            throw new BadRequestException(message, ErrorCode.INVALID_MEMBER_PROFILE_IMAGE_SIZE);
        }
    }
}
