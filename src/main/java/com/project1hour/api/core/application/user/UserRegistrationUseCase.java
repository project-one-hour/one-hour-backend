package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.data.ProfileImageInput;
import com.project1hour.api.core.application.user.data.event.UserRegisteredEvent;
import com.project1hour.api.core.application.user.exports.UserRegistrationService;
import com.project1hour.api.core.application.user.imports.OauthClient2.SocialProfileId;
import com.project1hour.api.core.application.user.imports.OauthClientFactory;
import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.entity.User.UserBuilder;
import com.project1hour.api.core.domain.user.value.AuthInfo;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationUseCase implements UserRegistrationService {

    private final CheckNicknameDuplicationUseCase checkNicknameDuplicationUseCase;
    private final SelectUserInterestUseCase selectUserInterestUseCase;
    private final ProfileImageConfigurationUseCase profileImageConfigurationUsecase;
    private final TokenAuthenticationUseCase tokenAuthenticationUseCase;

    private final UserRepository userRepository;
    private final OauthClientFactory oauthClientFactory;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Response signUpUser(final Request request) {
        checkNicknameDuplicationUseCase.checkIfNicknameDuplicate(request.nickname());

        List<Long> interestIds = request.interestIds();
        UserBuilder interestSelectedBuilder =
                selectUserInterestUseCase.selectInterestIds(User.builder(), interestIds);

        List<ProfileImageInput> profileImageInputs = request.profileImageInputs();
        UserBuilder profileImageConfiguredBuilder =
                profileImageConfigurationUsecase.configureProfileImages(interestSelectedBuilder, profileImageInputs);

        User registeredUser = registerUser(
                profileImageConfiguredBuilder,
                request.nickname(),
                request.gender(),
                request.birthday(),
                request.mbti(),
                request.marketingConsentAllowed(),
                request.notificationConsentAllowed(),
                request.socialProvider().provider(),
                request.socialProvider().accessToken(),
                request.socialProvider().refreshToken()
        );

        String accessToken = tokenAuthenticationUseCase.createAccessToken(registeredUser.getId());
        return new Response(accessToken);
    }

    /**
     * Command : 회원 가입
     */
    protected User registerUser(final UserBuilder userBuilder, final String nickname, final String gender,
                                final LocalDate birthday, final String mbti,
                                final boolean marketingConsentAllowed, final boolean notificationConsentAllowed,
                                final String provider, final String accessToken, final String refreshToken) {
        SocialProfileId socialProfileId =
                oauthClientFactory.getOauthClientByProvider(provider).findSocialProfileIdByAccessToken(accessToken);

        if (userRepository.existsAuthBySocialProfileId(socialProfileId.id())) {
            throw new BadRequestException("이미 가입한 사용자 입니다.", ErrorCode.DUPLICATED_SIGN_UP);
        }

        User newUser = userBuilder
                .withNickname(new Nickname(nickname))
                .withGender(Gender.find(gender))
                .withBirthday(new Birthday(birthday))
                .withMbti(Mbti.find(mbti))
                .serviceConsent(marketingConsentAllowed, notificationConsentAllowed)
                .userAuth(provider, new AuthInfo(socialProfileId.id(), accessToken, refreshToken))
                .build();

        User registeredUser = userRepository.save(newUser);
        applicationEventPublisher.publishEvent(new UserRegisteredEvent(registeredUser.getId()));
        return registeredUser;
    }
}
