package com.project1hour.api.global.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JwtTokenExtractorTest {

    private final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

    @Test
    void Authorization_헤더가_비어있으면_빈값을_반환한다() {
        // given
        given(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).willReturn(null);

        // when
        Optional<String> token = JwtTokenExtractor.extractToken(httpServletRequest);

        // then
        assertThat(token).isEmpty();

    }
    @ParameterizedTest
    @ValueSource(strings = {"", "asdf", "jwt.token.value", "Beaaa jwt.token.value"})
    void 헤더_형식이_일치하지_않으면_빈값을_반환한다(String invalidHeader) {
        // given
        given(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).willReturn(invalidHeader);

        // when
        Optional<String> token = JwtTokenExtractor.extractToken(httpServletRequest);

        // then
        assertThat(token).isEmpty();
    }

    @Test
    void 헤더_형식이_일치하면_토큰을_반환한다() {
        // given
        String header = "Bearer This_is.valid.TokenValue_-";
        given(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).willReturn(header);

        // when
        Optional<String> token = JwtTokenExtractor.extractToken(httpServletRequest);

        // then
        assertThat(token).isEqualTo(Optional.of("This_is.valid.TokenValue_-"));
    }
}
