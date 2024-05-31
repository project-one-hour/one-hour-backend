package com.project1hour.api.core.domain.member.profileinfo;

import com.project1hour.api.core.domain.image.ImageFile;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ProfileImageFiles(List<MultipartFile> profileImageFiles) {

    public static final int MAX_PROFILE_IMAGE_SIZE = 3;

    public ProfileImageFiles {
        validateSize(profileImageFiles);
    }

    private void validateSize(final List<MultipartFile> profileImageFiles) {
        if (profileImageFiles.isEmpty() || profileImageFiles.size() > MAX_PROFILE_IMAGE_SIZE) {
            String message = String.format("프로필 이미지는 1장 이상 3장 이하여야 합니다. size = %d", profileImageFiles.size());
            throw new BadRequestException(message, ErrorCode.INVALID_MEMBER_PROFILE_IMAGE_SIZE);
        }
    }

    public List<ImageFile> toImageFiles() {
        return profileImageFiles.stream()
                .map(ImageFile::from)
                .toList();
    }
}
