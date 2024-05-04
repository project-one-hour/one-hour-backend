package com.project1hour.api.core.infrastructure.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.core.domain.auth.Authority;
import com.project1hour.api.core.exception.auth.ExpiredTokenException;
import com.project1hour.api.core.exception.auth.InvalidTokenSignatureException;
import com.project1hour.api.core.exception.auth.MalformedTokenException;
import com.project1hour.api.core.exception.auth.UnsupportedTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JwtTokenProviderTest {

    private final String secretKey = "dGhpc2lzIGtleSBnZW5lcmF0b3IgZm9yIGJhc2UgNjQgZW5jb2RpbmcgdGhpcyBrZXkgd2lsbCB1c2UgaHMgNTEyIGFsZ29yaXRobSBzbyB3ZSBuZWVkIHNvbWUgc3RyaW5ncyBhc2Q7bGZramFzZGZsO2tqYXNkZmw7a2oK";
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(secretKey, 360000);

    @Test
    void 토큰을_생성할_수_있다() {
        // when
        String accessToken = jwtTokenProvider.generateAccessToken(String.valueOf(1), Authority.MEMBER);

        // then
        assertThat(accessToken).isNotNull();
    }

    @Test
    void 토큰에서_subject를_추출할_수_있다() {
        // given
        String validToken = Jwts.builder()
                .subject("1")
                .expiration(new Date(new Date().getTime() + 360000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();

        // when
        String subject = jwtTokenProvider.extractSubject(validToken);

        // then
        assertThat(subject).isEqualTo("1");
    }

    @Test
    void 토큰에서_Authority를_추출할_수_있다() {
        // given
        String validToken = Jwts.builder()
                .subject("1")
                .expiration(new Date(new Date().getTime() + 360000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .claim("Authority", Authority.MEMBER)
                .compact();

        // when
        Authority authority = jwtTokenProvider.extractAuthority(validToken);

        // then
        assertThat(authority).isEqualTo(Authority.MEMBER);
    }

    @Test
    void 만료된_토큰이라면_예외가_발생합니다() {
        // given
        String expiredToken = Jwts.builder()
                .subject("1")
                .expiration(new Date(new Date().getTime() - 360000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();

        // expect
        assertThatThrownBy(() -> jwtTokenProvider.extractSubject(expiredToken))
                .isInstanceOf(ExpiredTokenException.class)
                .hasMessage("만료된 토큰입니다.");
    }

    @Test
    void 유효하지_않은_토큰이라면_예외가_발생합니다() {
        // expect
        assertThatThrownBy(() -> jwtTokenProvider.extractSubject("invalidToken"))
                .isInstanceOf(MalformedTokenException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @Test
    void 잘못된_토큰시그니처라면_예외가_발생합니다() {
        // given
        String invalidSinatureToken = Jwts.builder()
                .subject("1")
                .expiration(new Date(new Date().getTime() + 360000))
                .signWith(Keys.hmacShaKeyFor("invalidSignature-asdfasdfasdfasdfasdfasdf".getBytes()))
                .compact();

        // expect
        assertThatThrownBy(() -> jwtTokenProvider.extractSubject(invalidSinatureToken))
                .isInstanceOf(InvalidTokenSignatureException.class)
                .hasMessage("잘못된 토큰 시그니처 입니다.");
    }

    @Test
    void 지원하지_않는_JWT토큰이라면_예외가_발생합니다() {
        // given
        String unsupportToken = Jwts.builder()
                .subject("1")
                .expiration(new Date(new Date().getTime() + 360000))
                .compact();

        // expect
        assertThatThrownBy(() -> jwtTokenProvider.extractSubject(unsupportToken))
                .isInstanceOf(UnsupportedTokenException.class)
                .hasMessage("지원하지 않는 토큰 형식입니다.");
    }
}
