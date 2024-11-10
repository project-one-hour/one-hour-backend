package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.exports.UserRegistrationService;
import com.project1hour.api.core.application.user.imports.UserApplicationRepository;
import com.project1hour.api.core.application.user.model.ProfileImageInfo;
import com.project1hour.api.core.application.user.model.ProfileImageInput;
import com.project1hour.api.core.application.user.model.event.UserRegisteredEvent;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.entity.User.UserBuilder;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.MarketingConsent;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.NotificationConsent;
import com.project1hour.api.core.domain.user.value.SignUpStatus;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRegistrationUseCase implements UserRegistrationService {

    private final CheckNicknameDuplicationUseCase checkNicknameDuplicationUseCase;
    private final SelectUserInterestUseCase selectUserInterestUseCase;
    private final ProfileImageConfigurationUseCase profileImageConfigurationUsecase;

    private final UserApplicationRepository userApplicationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void signUpUser(final Request request) {
        String nickname = request.nickname();
        checkNicknameDuplicationUseCase.checkIfNicknameDuplicate(nickname);

        List<Long> interestIds = request.interestIds();
        selectUserInterestUseCase.selectInterestIds(interestIds);

        List<ProfileImageInput> profileImageInputs = request.profileImageInputs();
        var profileImageInfos = profileImageConfigurationUsecase.configureProfileImages(profileImageInputs);

        registerUser(
                request.userId(),
                nickname,
                request.gender(),
                request.birthday(),
                request.mbti(),
                request.isMarketingAllowed(),
                request.isNotificationAllowed(),
                interestIds,
                profileImageInfos
        );
    }

    protected void registerUser(final Long userId, final String nickname, final String gender, final LocalDate birthday,
                                final String mbti, final boolean isMarketingAllowed,
                                final boolean isNotificationAllowed,
                                final List<Long> interestIds, final List<ProfileImageInfo> profileImageInfos) {

        User pendingUser = userApplicationRepository.findUserById(userId)
                .filter(User::isProfileRequired)
                .orElseThrow(() -> new NotFoundException("가입 대기 중인 회원을 찾을 수 없습니다.", ErrorCode.MEMBER_NOT_FOUND));

        UserBuilder userBuilder = pendingUser.toBuilder()
                .nickname(new Nickname(nickname))
                .gender(Gender.find(gender))
                .birthday(new Birthday(birthday))
                .mbti(Mbti.find(mbti))
                .marketingConsent(MarketingConsent.fromBoolean(isMarketingAllowed))
                .notificationConsent(NotificationConsent.fromBoolean(isNotificationAllowed))
                .signUpStatus(SignUpStatus.SIGNED_UP);

        interestIds.forEach(interestId -> userBuilder.userInterest(interestId));
        profileImageInfos.forEach(info -> userBuilder.profileImage(info.imageId(), info.isPrimary()));

        User registeredUser = userApplicationRepository.saveUser(userBuilder.build());
        eventPublisher.publishEvent(new UserRegisteredEvent(registeredUser.getId()));
    }
}
