package com.project1hour.api.core.application.user.exports;

import com.project1hour.api.core.application.user.data.ProfileImageInput;
import java.time.LocalDate;
import java.util.List;

public interface UserRegistrationService {

    Response signUpUser(Request request);

    interface Request {
        String nickname();

        String gender();

        LocalDate birthday();

        String mbti();

        List<Long> interestIds();

        boolean marketingConsentAllowed();

        boolean notificationConsentAllowed();

        SocialProvider socialProvider();

        List<ProfileImageInput> profileImageInputs();
    }

    record SocialProvider(
            String provider,
            String accessToken,
            String refreshToken) {
    }

    record Response(String accessToken) {
    }
}
