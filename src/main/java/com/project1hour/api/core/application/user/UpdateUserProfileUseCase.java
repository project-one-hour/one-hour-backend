package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.exports.UserRegistrationService;
import com.project1hour.api.core.application.user.imports.UserCommandPort;
import com.project1hour.api.core.application.user.model.ProfileImageInput;
import com.project1hour.api.core.application.user.model.event.UserUpdatedEvent;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.MarketingConsent;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.NotificationConsent;
import com.project1hour.api.core.domain.user.value.SignUpStatus;
import com.project1hour.api.core.domain.user.value.UserId;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UpdateUserProfileUseCase implements UserRegistrationService {

    private final CheckNicknameDuplicationUseCase checkNicknameDuplicationUseCase;
    private final UserInterestSelectionUseCase userInterestSelectionUseCase;
    private final ProfileImageConfigurationUseCase profileImageConfigurationUsecase;
    private final LoadAuthenticatedUserUseCase loadAuthenticatedUserUseCase;

    private final UserCommandPort userCommandPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void signUpUser(final Request request) {
        String nickname = request.nickname();
        checkNicknameDuplicationUseCase.checkIfNicknameDuplicate(nickname);

        UserId userId = new UserId(request.userId());
        User authenticatedUser = loadAuthenticatedUserUseCase.loadAuthenticatedUser(userId);

        User userWithUpdatedProfile = updateUser()
                .user(authenticatedUser)
                .nickname(nickname)
                .gender(request.gender())
                .birthday(request.birthday())
                .mbti(request.mbti())
                .isMarketingAllowed(request.isMarketingAllowed())
                .isNotificationAllowed(request.isNotificationAllowed())
                .update();

        List<Long> interestIds = request.interestIds();
        User userWithSelectedInterests = userInterestSelectionUseCase.selectInterests(userWithUpdatedProfile,
                interestIds);

        List<ProfileImageInput> profileImageInputs = request.profileImageInputs();
        profileImageConfigurationUsecase.configureProfileImages(userWithSelectedInterests, profileImageInputs);
    }

    @Builder(access = AccessLevel.PROTECTED, builderMethodName = "updateUser", buildMethodName = "update")
    protected User updateUserProfile(final User user, final String nickname, final String gender,
                                     final LocalDate birthday, final String mbti, final boolean isMarketingAllowed,
                                     final boolean isNotificationAllowed) {
        boolean isNewUser = user.isProfileRequired();

        User updatedUser = user.toBuilder()
                .nickname(new Nickname(nickname))
                .birthday(new Birthday(birthday))
                .gender(Gender.find(gender))
                .mbti(Mbti.find(mbti))
                .marketingConsent(MarketingConsent.fromBoolean(isMarketingAllowed))
                .notificationConsent(NotificationConsent.fromBoolean(isNotificationAllowed))
                .signUpStatus(SignUpStatus.SIGNED_UP)
                .build();

        User savedUser = userCommandPort.saveUser(updatedUser);
        eventPublisher.publishEvent(new UserUpdatedEvent(savedUser.getId(), isNewUser));
        return savedUser;
    }
}
