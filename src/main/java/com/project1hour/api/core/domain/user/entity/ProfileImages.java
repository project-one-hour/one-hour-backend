package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.value.ProfileImageType;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

/**
 * TODO : 대표 사진 validate 작성
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImages {

    private static final int MAX_PROFILE_IMAGE_SIZE = 3;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileImage> profileImageList;

    @Builder
    public ProfileImages(final List<Long> imageIds, final int primaryImageIndex, final User user) {
        validate(imageIds, primaryImageIndex);
        this.profileImageList = createProfileImageList(imageIds, primaryImageIndex, user);
    }

    private void validate(final List<Long> imageIds, final int primaryImageIndex) {
        if (CollectionUtils.isEmpty(imageIds) || imageIds.size() > MAX_PROFILE_IMAGE_SIZE) {
            String message = String.format("프로필 이미지는 1장 이상 3장 이하여야 합니다. size = %d", imageIds.size());
            throw new BadRequestException(message, ErrorCode.INVALID_MEMBER_PROFILE_IMAGE_SIZE);
        }

        if (primaryImageIndex < 0 || primaryImageIndex >= imageIds.size()) {
            String message = String.format("유효하지 않은 대표 프로필 사진 index입니다. index = %d image size = %d",
                    primaryImageIndex, imageIds.size());
            throw new BadRequestException(message, ErrorCode.INVALID_PRIMARY_IMAGE_INDEX);
        }
    }

    private List<ProfileImage> createProfileImageList(final List<Long> imageIds, final int primaryImageIndex,
                                                      final User user) {
        return IntStream.range(0, imageIds.size())
                .mapToObj(index -> ProfileImage.builder()
                        .imageId(imageIds.get(index))
                        .profileImageType(
                                index == primaryImageIndex ? ProfileImageType.PRIMARY : ProfileImageType.SECONDARY)
                        .user(user)
                        .build())
                .toList();
    }

}
