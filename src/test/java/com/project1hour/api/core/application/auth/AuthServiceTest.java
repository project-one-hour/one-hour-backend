package com.project1hour.api.core.application.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
import com.project1hour.api.core.domain.auth.AuthenticationContext;
import com.project1hour.api.core.implement.auth.SocialProfileReader;
import com.project1hour.api.core.implement.auth.dto.TokenResponse;
import com.project1hour.api.core.infrastructure.auth.apple.AppleSocialInfo;
import com.project1hour.api.core.infrastructure.auth.kakao.KakaoSocialInfo;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = NONE)
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthProviderRepository authProviderRepository;

    @MockBean
    private AuthenticationContext authenticationContext;

    @MockBean
    private SocialProfileReader socialProfileReader;

    @Nested
    class createToken메소드는 {

        @Nested
        class Provider정보를_찾을수없다면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), any())).willThrow(
                        new NotFoundException("지원하는 소셜 로그인이 없습니다.", ErrorCode.OAUTH_PROVIDER_NOT_FOUND));
            }

            @Test
            void 예외가_발생한다() {
                // expect
                assertThatThrownBy(() -> authService.createToken("inKAKAO", "token"))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("지원하는 소셜 로그인이 없습니다.");
            }
        }

        @Nested
        class 최초로_소셜로그인을_진행하면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), any())).willReturn(
                        new KakaoSocialInfo("providerId", "test@mail.com")
                );
            }

            @Test
            void 토큰값과_새로운_회원여부의_boolean값을_true로_반환한다() {
                // when
                TokenResponse token = authService.createToken("KAKAO", "validToken");

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
                        new KakaoSocialInfo("providerId", "test@mail.com")
                );
                authService.createToken("KAKAO", "validToken");
            }

            @Test
            void 토큰값과_새로운_회원여부의_boolean값을_false로_반환한다() {
                // when
                TokenResponse token = authService.createToken("KAKAO", "validToken");

                // then
                assertAll(
                        () -> assertThat(token.accessToken()).isNotNull(),
                        () -> assertThat(token.isNew()).isFalse()
                );
            }

        }

        @Nested
        class 동일한_이메일을_사용하는_기존_소셜로그인_이력이_존재하면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), any())).willReturn(
                        new KakaoSocialInfo("providerId", "test@mail.com")
                );
                authService.createToken("KAKAO", "validToken");
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
                        new KakaoSocialInfo("providerId", "test@mail.com"));
                given(socialProfileReader.read(any(), eq("newEmailToken"))).willReturn(
                        new KakaoSocialInfo("providerId", "new@mail.com"));
                authService.createToken("KAKAO", "originalEmailToken");
            }

            @Test
            void 새로운_이메일로_업데이트한다() {
                // when
                authService.createToken("KAKAO", "newEmailToken");
                AuthProvider authProvider = authProviderRepository.findByProviderIdOrEmail("providerId", "new@mail.com")
                        .get();

                // then
                assertAll(
                        () -> assertThat(authProvider).extracting(AuthProvider::getProviderId).isEqualTo("providerId"),
                        () -> assertThat(authProvider).extracting(AuthProvider::getEmail).isEqualTo("new@mail.com")
                );
            }

        }

        @Nested
        class 동일한_이메일을_사용한_다른_소셜로그인_이력이_존재하면 {

            @BeforeEach
            void setUp() {
                given(socialProfileReader.read(any(), eq("originalEmailToken"))).willReturn(
                        new AppleSocialInfo("providerId", "test@mail.com"));
                given(socialProfileReader.read(any(), eq("otherProviderSameEmailToken"))).willReturn(
                        new KakaoSocialInfo("providerId", "test@mail.com"));
                authService.createToken("APPLE", "originalEmailToken");
            }

            @Test
            void 예외가_발생한다() {
                // expect
                assertThatThrownBy(() -> authService.createToken("KAKAO", "otherProviderSameEmailToken"))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage("이미 APPLE로 소셜로그인을 한 내역이 있습니다. current = KAKAO");
            }
        }
    }
}
