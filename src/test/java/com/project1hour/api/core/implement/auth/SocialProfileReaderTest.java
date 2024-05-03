package com.project1hour.api.core.implement.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.core.exception.auth.OauthProviderNotFound;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SocialProfileReaderTest {

    @Autowired
    private SocialProfileReader socialProfileReader;

    @Test
    void 일치하는_Provider를_찾을_수_없다면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> socialProfileReader.read("invalidProvider", "token"))
                .isInstanceOf(OauthProviderNotFound.class)
                .hasMessageContaining("지원하는 소셜 로그인이 없습니다.");
    }
}
