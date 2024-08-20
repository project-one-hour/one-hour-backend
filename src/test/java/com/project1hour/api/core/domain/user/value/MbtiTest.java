package com.project1hour.api.core.domain.user.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.global.advice.BadRequestException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MbtiTest {

    @Test
    void 일치하는_MBTI가_없으면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> Mbti.find("esff"))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("ESFF와 일치하는 MBTI를 찾을 수 없습니다.");
    }

    @Test
    void MBTI를_찾을_수_있다() {
        // when
        Mbti actual = Mbti.find("ENTJ");

        // then
        assertThat(actual).isEqualTo(Mbti.ENTJ);
    }
}
