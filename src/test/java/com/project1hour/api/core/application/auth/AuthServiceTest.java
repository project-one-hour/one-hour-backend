package com.project1hour.api.core.application.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project1hour.api.core.exception.auth.OauthProviderNotFound;
import com.project1hour.api.core.implement.auth.SocialProfileReader;
import com.project1hour.api.core.implement.auth.dto.KakaoSocialInfoResponse;
import com.project1hour.api.core.implement.auth.dto.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private SocialProfileReader socialProfileReader;

    @Nested
    class createToken메소드는 {

        @Nested
        class Provider정보를_찾을수없다면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), any())).willThrow(new OauthProviderNotFound());
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> authService.createToken("invalidProvider", "token"))
                        .isInstanceOf(OauthProviderNotFound.class)
                        .hasMessage("지원하는 소셜 로그인이 없습니다.");
            }
        }

        @Nested
        class 최초로_소셜로그인을_진행하면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), any())).willReturn(
                        new KakaoSocialInfoResponse("providerId", "test@mail.com")
                );
            }

            @Test
            void 토큰값과_새로_가입_했다는_boolean값을_반환한다() {
                // when
                TokenResponse token = authService.createToken("validProvider", "validToken");

                // then
                assertAll(
                        () -> assertThat(token.accessToken()).isNotNull(),
                        () -> assertThat(token.isNew()).isTrue()
                );
            }
        }

        @Nested
        class 기존에_동일한_소셜로그인_이력이_존재하면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), any())).willReturn(
                        new KakaoSocialInfoResponse("providerId", "test@mail.com")
                );
                authService.createToken("validProvider", "validToken");
            }

            @Test
            void 토큰값과_기존_회원이라는_boolean값을_반환한다() {
                // when
                TokenResponse token = authService.createToken("validProvider", "validToken");

                // then
                assertAll(
                        () -> assertThat(token.accessToken()).isNotNull(),
                        () -> assertThat(token.isNew()).isFalse()
                );
            }
        }
    }
}
