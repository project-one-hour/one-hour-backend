package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.api.OauthClientFactory;
import com.project1hour.api.core.application.user.api.SocialProfileId;
import com.project1hour.api.core.application.user.service.UserRegistrationService;
import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.value.AuthInfo;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.ServiceConsent;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationUseCase implements UserRegistrationService {

    private final CheckNicknameDuplicationUseCase checkNicknameDuplicationUseCase;
    private final SelectUserInterestUseCase selectUserInterestUseCase;
    private final ProfileImageConfigurationUseCase profileImageConfigurationUsecase;

    private final UserRepository userRepository;
    private final OauthClientFactory oauthClientFactory;

    @Override
    public Long signUpUser(final UserRegistrationService.Request request) {
        checkNicknameDuplicationUseCase.checkIfNicknameDuplicate(request.nickname());

        User registeredUser = registerUser(
                request.nickname(),
                request.gender(),
                request.birthday(),
                request.mbti(),
                request.marketingConsentAllowed(),
                request.notificationConsentAllowed(),
                request.socialProvider().provider(),
                request.socialProvider().accessToken(),
                request.socialProvider().refreshToken(),
                selectUserInterestUseCase.selectInterestIds(request.interestIds()),
                profileImageConfigurationUsecase.configureProfileImages(request.imageFiles()),
                request.primaryImageFileIndex()
        );
        return registeredUser.getId();
    }

    /**
     * Command : 회원 가입
     */
    protected User registerUser(final String nickname, final String gender, final LocalDate birthday, final String mbti,
                                final boolean marketingConsentAllowed, final boolean notificationConsentAllowed,
                                final String provider, final String accessToken, final String refreshToken,
                                final Set<Long> interestIds, final List<Long> imageIds, final int primaryImageIndex) {
        SocialProfileId socialProfileId =
                oauthClientFactory.getOauthClientByProvider(provider).findSocialProfileIdByAccessToken(accessToken);

        if (userRepository.existsAuthBySocialProfileId(socialProfileId.id())) {
            throw new BadRequestException("이미 가입한 사용자 입니다.", ErrorCode.DUPLICATED_SIGN_UP);
        }

        User newUser = User.createNewUser()
                .nickname(new Nickname(nickname))
                .gender(Gender.find(gender))
                .birthday(new Birthday(birthday))
                .mbti(Mbti.find(mbti))
                .serviceConsent(
                        ServiceConsent.of()
                                .marketingConsentAllowed(marketingConsentAllowed)
                                .notificationConsentAllowed(notificationConsentAllowed)
                                .build()
                )
                .interestIds(interestIds)
                .imageIds(imageIds)
                .primaryImageIndex(primaryImageIndex)
                .provider(provider)
                .authInfo(new AuthInfo(socialProfileId.id(), accessToken, refreshToken))
                .build();

        User registeredUser = userRepository.save(newUser);
        // DomainEvent 발행
        return registeredUser;
    }
}
