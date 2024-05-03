package com.project1hour.api.core.implement.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.core.exception.auth.OauthProviderNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocialProfileReaderTest {

    @Autowired
    private SocialProfileReader socialProfileReader;

    @Test
    @DisplayName("일치하는 Provider를 찾을 수 없다면 예외가 발생한다.")
    void read() {
        // expect
        assertThatThrownBy(() -> socialProfileReader.read("invalidProvider", "token"))
                .isInstanceOf(OauthProviderNotFound.class)
                .hasMessageContaining("지원하는 소셜 로그인이 없습니다.");
    }
}
