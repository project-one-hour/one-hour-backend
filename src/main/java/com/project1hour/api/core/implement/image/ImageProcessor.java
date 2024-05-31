package com.project1hour.api.core.implement.image;

import com.project1hour.api.core.domain.image.ImageUploader;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.profileinfo.ProfileImageFiles;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageProcessor {

    private final ImageUploader imageUploader;

    public List<String> uploadProfileImages(final Member member, final List<MultipartFile> images) {
        ProfileImageFiles profileImageFiles = new ProfileImageFiles(images);

        if (member.hasAnyProfileImage()) {
            throw new BadRequestException("이미 최초 프로필 등록을 완료했습니다.", ErrorCode.ALREADY_UPLOAD_NEW_PROFILE_IMAGE);
        }

        return profileImageFiles.toImageFiles()
                .stream()
                .map(imageUploader::upload)
                .toList();
    }
}
