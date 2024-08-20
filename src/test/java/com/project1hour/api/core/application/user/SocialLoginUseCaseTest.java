package com.project1hour.api.core.application.user;

import static com.project1hour.api.core.domain.user.entity.UserTest.ADULT_BIRTH;
import static com.project1hour.api.core.domain.user.entity.UserTest.ALL_ALLOWED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project1hour.api.core.application.user.api.OauthClient2;
import com.project1hour.api.core.application.user.api.OauthClientFactory;
import com.project1hour.api.core.application.user.api.SocialProfileId;
import com.project1hour.api.core.application.user.service.SocialLoginService;
import com.project1hour.api.core.application.user.service.SocialLoginService.Request;
import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.value.AuthInfo;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.NotFoundException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class SocialLoginUseCaseTest {

    @Autowired
    private SocialLoginUseCase socialLoginUseCase;

    @Autowired
    private SocialLoginService socialLoginService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private OauthClientFactory oauthClientFactory;

    @MockBean
    private OauthClient2 oauthClient;

    private User user;

    @BeforeEach
    void setUp() {
        User dummyUser = User.createNewUser()
                .nickname(new Nickname("John"))
                .gender(Gender.find("MALE"))
                .birthday(new Birthday(ADULT_BIRTH))
                .mbti(Mbti.find("ENTP"))
                .serviceConsent(ALL_ALLOWED)
                .interestIds(Set.of(1L, 2L, 3L, 4L, 5L))
                .imageIds(List.of(1L, 2L, 3L))
                .primaryImageIndex(0)
                .authInfo(new AuthInfo("socialProfileId", "aToken", "rToken"))
                .provider("kakao")
                .build();

        user = userRepository.save(dummyUser);
        given(oauthClient.isSupport(any())).willReturn(true);
    }

    @Nested
    class 소셜_로그인_유스케이스는 {

        @Test
        void 소셜_프로필_ID로_사용자를_인증할_수_없을_때_예외가_발생한다() {
            // given
            SocialProfileId socialProfileId = () -> "이상한 값!";
            given(oauthClientFactory.getOauthClientByProvider("kakao")).willReturn(oauthClient);
            given(oauthClient.findSocialProfileIdByAccessToken("aToken")).willReturn(socialProfileId);

            // expect
            assertThatThrownBy(() ->
                    socialLoginUseCase.authenticateUserWithSocialProfile("kakao", "aToken"))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("해당하는 사용자를 찾을 수 없습니다.");
        }

        @Test
        void 소셜_로그인_제공자와_일치하지_않는_경우_예외가_발생한다() {
            // given
            SocialProfileId socialProfileId = () -> "socialProfileId";
            given(oauthClientFactory.getOauthClientByProvider("apple")).willReturn(oauthClient);
            given(oauthClient.findSocialProfileIdByAccessToken("aToken")).willReturn(socialProfileId);

            // expect
            assertThatThrownBy(() ->
                    socialLoginUseCase.authenticateUserWithSocialProfile("apple", "aToken"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("소셜 로그인 제공자가 일치하지 않습니다.");
        }
    }

    @Nested
    class 소셜_로그인_서비스는 {

        @Test
        void 정상적으로_소셜_로그인을_성공하면_사용자의_ID를_반환한다() {
            // given
            SocialProfileId socialProfileId = () -> "socialProfileId";
            given(oauthClientFactory.getOauthClientByProvider("kakao")).willReturn(oauthClient);
            given(oauthClient.findSocialProfileIdByAccessToken("aToken")).willReturn(socialProfileId);

            // when
            Long authenticatedUserId = socialLoginService.login(new Request("kakao", "aToken"));

            // then
            assertThat(authenticatedUserId).isEqualTo(user.getId());
        }
    }
}
