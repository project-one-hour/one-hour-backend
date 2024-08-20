package com.project1hour.api.core.domain.user.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.project1hour.api.global.advice.BadRequestException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AuthProviderTest {

    @ParameterizedTest
    @ValueSource(strings = {"NAVER", "GOOGLE", "GITHUB"})
    void 일치하는_Provider가_없으면_예외가_발생한다(String providerName) {
        assertThatThrownBy(() -> AuthProvider.find(providerName))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(String.format("지원하지 않는 소셜 인증입니다 . input = %s", providerName));
    }

    @Test
    void AuthProvider를_찾을_수_있다() {
        // when
        AuthProvider appleProvider = AuthProvider.find("apple");
        AuthProvider kakaoProvider = AuthProvider.find("KAKAO");

        // then
        assertAll(
                () -> assertThat(appleProvider).isEqualTo(AuthProvider.APPLE),
                () -> assertThat(kakaoProvider).isEqualTo(AuthProvider.KAKAO)
        );
    }
}
