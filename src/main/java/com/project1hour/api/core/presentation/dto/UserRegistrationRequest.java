package com.project1hour.api.core.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project1hour.api.core.application.user.exports.UserRegistrationService;
import com.project1hour.api.core.application.user.model.ProfileImageInput;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.With;
import org.springframework.web.multipart.MultipartFile;


public record UserRegistrationRequest(
        @With @JsonIgnore Long userId,
        String nickname,
        String gender,
        LocalDate birthday,
        String mbti,
        List<Long> interestIds,
        boolean isMarketingAllowed,
        boolean isNotificationAllowed,
        @With @JsonIgnore MultipartFile primaryImage,
        @With @JsonIgnore List<MultipartFile> secondaryImages
) implements UserRegistrationService.Request {

    @Override
    public List<ProfileImageInput> profileImageInputs() {
        var profileImageEntry = Stream.of(new ProfileImageInputImpl(primaryImage, true));
        var secondaryImageEntries = Optional.ofNullable(secondaryImages)
                .orElseGet(List::of)
                .stream()
                .map(secondaryImage -> new ProfileImageInputImpl(secondaryImage, false));

        return Stream.concat(profileImageEntry, secondaryImageEntries).collect(Collectors.toUnmodifiableList());
    }

    record ProfileImageInputImpl(
            MultipartFile profileImage,
            boolean isPrimary
    ) implements ProfileImageInput {

        @Override
        public InputStream inputStream() {
            try {
                return profileImage.getInputStream();
            } catch (IOException e) {
                throw new InfraStructureException("이미지를 가져오는 중 오류가 발생했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
