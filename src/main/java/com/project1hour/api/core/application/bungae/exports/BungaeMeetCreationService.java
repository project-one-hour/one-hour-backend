package com.project1hour.api.core.application.bungae.exports;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

public interface BungaeMeetCreationService {
    void createMeetBungae(Request request);

    interface Request {

        Long userId();

        String title();

        String bungaeDescription();

        int ageLowerLimit();

        int ageUpperLimit();

        String genderType();

        boolean requiredMannerUser();

        Long categoryId();

        String locationDescription();

        double latitude();

        double longitude();

        int capacity();

        LocalDateTime startAt();

        Optional<InputStream> optionalImageInput();
    }
}
