package com.project1hour.api.core.application.user;

import static com.project1hour.api.core.domain.user.entity.UserTest.ADULT_BIRTH;
import static com.project1hour.api.core.domain.user.entity.UserTest.ALL_ALLOWED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project1hour.api.core.application.user.api.OauthClient2;
import com.project1hour.api.core.application.user.api.SocialProfileId;
import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.value.AuthInfo;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.global.advice.BadRequestException;
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
public class UserRegistrationUseCaseTest {

    @Autowired
    private UserRegistrationUseCase userRegistrationUseCase;

    @MockBean
    private OauthClient2 oauthClient;

    @Autowired
    private UserRepository userRepository;

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
        userRepository.save(dummyUser);
        given(oauthClient.isSupport(any())).willReturn(true);
    }

    @Nested
    class 회원가입_유스케이스는 {

        @Test
        void 사용자가_이미_가입된_경우_예외가_발생한다() {
            SocialProfileId socialProfileId = () -> "socialProfileId";
            given(oauthClient.findSocialProfileIdByAccessToken("aToken")).willReturn(socialProfileId);

            assertThatThrownBy(() ->
                    userRegistrationUseCase.registerUser(
                            "John",
                            "MALE",
                            ADULT_BIRTH,
                            "ENTP",
                            true, true,
                            "kakao", "aToken", "rToken",
                            Set.of(1L, 2L, 3L, 4L, 5L),
                            List.of(1L, 2L, 3L), 0
                    ))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("이미 가입한 사용자 입니다.");
        }
    }
}
