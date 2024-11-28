package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.imports.UserCommandPort;
import com.project1hour.api.core.application.user.model.ProfileImageInput;
import com.project1hour.api.core.application.user.model.event.ProfileImageConfiguredEvent;
import com.project1hour.api.core.application.user.model.event.ProfileImageConfiguredEvents;
import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.entity.User.UserBuilder;
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
    private final UserCommandPort userCommandPort;

    /**
     * Command : 프로필 사진 설정 <br/>
     */
    protected User configureProfileImages(final User user, final List<ProfileImageInput> profileImageInputs) {
        UserBuilder userBuilder = user.toBuilder();
        List<ProfileImageConfiguredEvent> profileImageConfiguredEventList = new ArrayList<>();

        for (ProfileImageInput profileImageInput : profileImageInputs) {
            ImageId generatedProfileImageId = new ImageId(IdGenerator.generateId());
            var event = new ProfileImageConfiguredEvent(generatedProfileImageId, profileImageInput.inputStream());

            profileImageConfiguredEventList.add(event);
            userBuilder.profileImage(generatedProfileImageId, profileImageInput.isPrimary());
        }

        eventPublisher.publishEvent(new ProfileImageConfiguredEvents(profileImageConfiguredEventList));
        User userWithProfileImage = userBuilder.buildWithAggregation();
        return userCommandPort.saveProfileImagesByUser(userWithProfileImage);
    }
}
