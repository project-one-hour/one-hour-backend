package com.project1hour.api.core.domain.user.value;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.global.advice.BadRequestException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * TODO : 음절+영어 닉네임 테스트 고민 중..
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class NicknameTest {

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"ㅎㅏ", "ㅡㅇ", "ㅎ123", "1ㅎ", "음절인데&-`"})
    void 한글단독_혹은_한글과_숫자로_구성된_닉네임은_한글이_음절로_이루어져_있지_않다면_예외가_발생한다(String nickname) {
        // expect
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("닉네임의 형식이 잘못됐습니다. 현재닉네임 = " + nickname);
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"sㄱ1", "ㄱ가1a", "ㄱr1", "가1aㅏ"})
    void 영어가_포함된_닉네임이라면_한글의_자음_모음이나_숫자_모두_상관없이_닉네임을_생성할_수_있다(String nickname) {
        // expect
        assertThatNoException().isThrownBy(() -> new Nickname(nickname));
    }

    @Test
    void 숫자로만_이루어진_닉네임을_생성할_수_있다() {
        // expect
        assertThatNoException().isThrownBy(() -> new Nickname("12345678"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"가능한닉네임일까요", "놉"})
    void 닉네임의_길이가_2글자_이상_8글자_이하가_아니면_예외가_발생한다(String nickname) {
        // expect
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("닉네임은 2글자 이상 8글자 이하여야 합니다. 현재길이 = " + nickname.length());
    }
}
