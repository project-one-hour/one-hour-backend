package com.project1hour.api.core.domain.interest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.global.advice.BadRequestException;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InterestTest {

    @Test
    void 존재하지_않는_관심사_이름이_하나라도_포함되면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> Interest.findAllByNames(List.of("X", "와인/바", "디저트")))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("존재하지 않는 관심사 입니다. name = X");
    }

    @Test
    void 이름에_해당하는_관심사들을_찾을_수_있다() {
        // given
        List<String> interestNames = List.of("와인/바", "디저트", "커피", "반려동물", "영화");

        // when
        List<Interest> interests = Interest.findAllByNames(interestNames);

        // then
        assertThat(interests).containsExactly(Interest.WINE_BAR, Interest.DESSERT, Interest.COFFEE,
                Interest.PET, Interest.MOVIE);
    }

    @Test
    void 모든_관심사를_찾을_수_있다() {
        // expect
        assertThatNoException().isThrownBy(Interest::findAll);
    }
}
