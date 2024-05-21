package com.project1hour.api.core.domain.member.profileinfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.project1hour.api.global.advice.BadRequestException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GenderTest {

    @Test
    void 일치하는_성별이_없으면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> Gender.find("INVALID_VALUE"))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("일치하는 성별을 찾을 수 없습니다. 입력성별 = INVALID_VALUE");
    }

    @Test
    void 성별을_찾을_수_있다() {
        // when
        Gender male = Gender.find("male");
        Gender female = Gender.find("FEMALE");

        // then
        assertAll(
                () -> assertThat(male).isEqualTo(Gender.MALE),
                () -> assertThat(female).isEqualTo(Gender.FEMALE)
        );
    }
}
