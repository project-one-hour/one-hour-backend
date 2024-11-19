package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.model.ProfileImageInfo;
import com.project1hour.api.core.application.user.model.ProfileImageInput;
import com.project1hour.api.core.application.user.model.event.ProfileImageConfiguredEvent;
import com.project1hour.api.core.application.user.model.event.ProfileImageConfiguredEvents;
import com.project1hour.api.global.support.IdGenerator;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileImageConfigurationUseCase {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Command : 프로필 사진 설정 <br/>
     */
    protected List<ProfileImageInfo> configureProfileImages(final List<ProfileImageInput> profileImageInputs) {
        List<ProfileImageInfo> profileImageInfoList = new ArrayList<>();
        List<ProfileImageConfiguredEvent> profileImageConfiguredEventList = new ArrayList<>();

        for (ProfileImageInput profileImageInput : profileImageInputs) {
            Long generatedProfileImageId = IdGenerator.generateId();

            var event = new ProfileImageConfiguredEvent(generatedProfileImageId, profileImageInput.inputStream());
            profileImageConfiguredEventList.add(event);

            var imageInfo = new ProfileImageInfo(generatedProfileImageId, profileImageInput.isPrimary());
            profileImageInfoList.add(imageInfo);
        }

        eventPublisher.publishEvent(new ProfileImageConfiguredEvents(profileImageConfiguredEventList));
        return profileImageInfoList;
    }
}
