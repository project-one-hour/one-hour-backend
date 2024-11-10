package com.project1hour.api.core.application.user.exports;

import com.project1hour.api.core.application.user.model.ProfileImageInput;
import java.time.LocalDate;
import java.util.List;

public interface UserRegistrationService {

    void signUpUser(Request request);

    interface Request {
        Long userId();

        String nickname();

        String gender();

        LocalDate birthday();

        String mbti();

        List<Long> interestIds();

        boolean isMarketingAllowed();

        boolean isNotificationAllowed();

        List<ProfileImageInput> profileImageInputs();
    }
}
