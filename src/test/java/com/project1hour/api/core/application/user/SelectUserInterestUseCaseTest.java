package com.project1hour.api.core.application.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project1hour.api.core.application.user.api.UserInterestClient;
import com.project1hour.api.global.advice.BadRequestException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class SelectUserInterestUseCaseTest {

    @Autowired
    private SelectUserInterestUseCase selectUserInterestUseCase;

    @MockBean
    private UserInterestClient userInterestClient;

    @Test
    void 모든_관심사가_존재할_때_관심사_아이디_Set을_반환한다() {
        // given
        List<Long> interestIds = List.of(1L, 2L, 3L, 4L, 5L);
        given(userInterestClient.hasMissingInterestIds(any())).willReturn(false);

        // when
        Set<Long> result = selectUserInterestUseCase.selectInterestIds(interestIds);

        // expect
        assertThat(result)
                .isInstanceOf(Set.class)
                .hasSize(5)
                .containsExactlyElementsOf(interestIds);
    }

    @Test
    void 중복된_관심사가_존재할_때_예외가_발생한다() {
        // given
        List<Long> interestIds = List.of(1L, 2L, 2L, 4L, 5L);
        given(userInterestClient.hasMissingInterestIds(any())).willReturn(false);

        // expect
        assertThatThrownBy(() -> selectUserInterestUseCase.selectInterestIds(interestIds))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("종복 된 값이 1개 존재합니다.");
    }

    @Test
    void 존재하지_않은_관심사가_존재할_때_예외가_발생한다() {
        // given
        List<Long> interestIds = List.of(1L, 2L, 3L, 4L, 5L);
        given(userInterestClient.hasMissingInterestIds(any())).willReturn(true);

        // expect
        assertThatThrownBy(() -> selectUserInterestUseCase.selectInterestIds(interestIds))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("알 수 없는 관심사 값이 포함되어 있습니다.");
    }
}
