package com.project1hour.api.core.application.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
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
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthProviderRepository authProviderRepository;

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
            void 토큰값과_새로운_회워여부의_boolean값을_true로_반환한다() {
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
        class 기존_소셜로그인_이력이_존재하면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), any())).willReturn(
                        new KakaoSocialInfoResponse("providerId", "test@mail.com")
                );
                authService.createToken("validProvider", "validToken");
            }

            @Test
            void 토큰값과_새로운_회원여부의_boolean값을_false로_반환한다() {
                // when
                TokenResponse token = authService.createToken("validProvider", "validToken");

                // then
                assertAll(
                        () -> assertThat(token.accessToken()).isNotNull(),
                        () -> assertThat(token.isNew()).isFalse()
                );
            }
        }

        @Nested
        class 동일한_이메일을_사용하는_다른_소셜로그인_이력이_존재하면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), any())).willReturn(
                        new KakaoSocialInfoResponse("providerId", "test@mail.com")
                );
                authService.createToken("validProvider", "validToken");
            }

            @Test
            void 토큰값과_새로운_회원여부의_boolean값을_false로_반환한다() {
                // when
                TokenResponse token = authService.createToken("otherProviderId", "test@mail.com");

                // then
                assertAll(
                        () -> assertThat(token.accessToken()).isNotNull(),
                        () -> assertThat(token.isNew()).isFalse()
                );
            }
        }

        @Nested
        class 기존_소셜로그인_이력이_존재하지만_다른_이메일로_변경됐다면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), eq("originalEmailToken"))).willReturn(
                        new KakaoSocialInfoResponse("providerId", "test@mail.com"));
                given(socialProfileReader.read(any(), eq("newEmailToken"))).willReturn(
                        new KakaoSocialInfoResponse("providerId", "new@mail.com"));
                authService.createToken("validProvider", "originalEmailToken");
            }

            @Test
            void 새로운_이메일로_업데이트한다() {
                // when
                authService.createToken("validProvider", "newEmailToken");
                AuthProvider authProvider = authProviderRepository.findByProviderId("providerId").get();

                // then
                assertThat(authProvider.getEmail()).isEqualTo("new@mail.com");
            }
        }
    }
}
