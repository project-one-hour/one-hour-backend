package com.project1hour.api.core.application.user.exports;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

public interface UserRegistrationService {

    Long signUpUser(Request request);

    //인터페이스
    record Request(
            String nickname,
            String gender,
            LocalDate birthday,
            String mbti,
            List<Long> interestIds,
            boolean marketingConsentAllowed,
            boolean notificationConsentAllowed,
            SocialProvider socialProvider,
            List<InputStream> imageFiles,
            int primaryImageFileIndex) {
    }

    record SocialProvider(
            String provider,
            String accessToken,
            String refreshToken) {
    }

}
