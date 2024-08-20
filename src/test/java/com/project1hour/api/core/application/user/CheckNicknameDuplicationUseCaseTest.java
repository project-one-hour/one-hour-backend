package com.project1hour.api.core.application.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.project1hour.api.core.application.user.service.CheckNickNameDuplicationService;
import com.project1hour.api.core.application.user.service.CheckNickNameDuplicationService.Request;
import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.global.advice.BadRequestException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CheckNicknameDuplicationUseCaseTest {

    @Autowired
    private CheckNicknameDuplicationUseCase checkNicknameDuplicationUseCase;

    @Autowired
    private CheckNickNameDuplicationService checkNicknameDuplicationService;

    @MockBean
    private UserRepository userRepository;

    @Nested
    class 닉네임_중복_확인_유스케이스는 {

        @Test
        void 닉네임_중복_시_예외가_발생한다() {
            // given
            String nickname = "Test";
            given(userRepository.existsByNickname(nickname)).willReturn(true);

            // expect
            assertThatThrownBy(() -> checkNicknameDuplicationUseCase.checkIfNicknameDuplicate(nickname))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining("닉네임이 이미 존재합니다. 현재닉네임 = " + nickname);
        }
    }

    @Nested
    class 닉네임_중복_확인_서비스는 {

        @Test
        void 닉네임이_중복되지_않을_경우_false를_반환한다() {
            // given
            String nickname = "Test";
            given(userRepository.existsByNickname(nickname)).willReturn(false);

            // expect
            assertThat(checkNicknameDuplicationService.checkNickNameDuplication(new Request("Test")))
                    .isFalse();
        }
    }
}
