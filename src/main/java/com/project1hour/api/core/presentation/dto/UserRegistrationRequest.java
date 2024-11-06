package com.project1hour.api.core.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project1hour.api.core.application.user.exports.UserRegistrationService;
import com.project1hour.api.core.application.user.model.ProfileImageInput;
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
        @With Long userId,
        String nickname,
        String gender,
        LocalDate birthday,
        String mbti,
        List<Long> interestIds,
        boolean marketingConsentAllowed,
        boolean notificationConsentAllowed,
        String provider,
        @With @JsonIgnore MultipartFile primaryImage,
        @With @JsonIgnore List<MultipartFile> secondaryImages
) implements UserRegistrationService.Request {

    public List<ProfileImageInput> profileImageInputs() {
        return Stream.concat(
                Stream.of(new ProfileImageInputImpl(primaryImage, true)),
                Optional.ofNullable(secondaryImages).orElseGet(List::of).stream()
                        .map(secondaryImage -> new ProfileImageInputImpl(secondaryImage, false))
        ).collect(Collectors.toUnmodifiableList());
    }

    record ProfileImageInputImpl(MultipartFile profileImage, boolean isPrimary) implements ProfileImageInput {

        public InputStream inputStream() {
            try (InputStream imageInput = profileImage.getInputStream()) {
                return imageInput;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
