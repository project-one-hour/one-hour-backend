package com.project1hour.api.core.infrastructure.auth.jwt;

import com.project1hour.api.core.domain.auth.TokenProvider;
import com.project1hour.api.core.domain.member.Authority;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider implements TokenProvider {

    private static final String AUTHORITY = "Authority";

    private final SecretKey signingKey;
    private final long accessTokenExpireMilliSecond;

    public JwtTokenProvider(@Value("${jwt.token.secret-key}") final String signingKey,
                            @Value("${jwt.token.expire-length.access}") final long accessTokenExpireMilliSecond) {
        byte[] keyBytes = signingKey.getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpireMilliSecond = accessTokenExpireMilliSecond;
    }

    @Override
    public String generateAccessToken(final String subject, final Authority authority) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(issuedNow())
                .expiration(expiredAt(accessTokenExpireMilliSecond))
                .signWith(signingKey)
                .claim(AUTHORITY, authority)
                .compact();
    }

    @Override
    public String extractSubject(final String token) {
        return extractBody(token).getSubject();
    }

    @Override
    public Authority extractAuthority(final String token) {
        String authorityValue = extractBody(token).get(AUTHORITY, String.class);

        return Authority.valueOf(authorityValue);
    }

    private Claims extractBody(final String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException e) {
            throw new InfraStructureException("잘못된 토큰 시그니처 입니다.", ErrorCode.INVALID_TOKEN_SIGNATURE);
        } catch (UnsupportedJwtException e) {
            throw new InfraStructureException("지원하지 않는 토큰 형식입니다.", ErrorCode.UNSUPPORTED_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new InfraStructureException("만료된 토큰입니다.", ErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new InfraStructureException("유효하지 않은 토큰입니다.", ErrorCode.MALFORMED_TOKEN);
        }
    }

    private Date issuedNow() {
        LocalDateTime now = LocalDateTime.now();

        return Date.from(now.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public Date expiredAt(final long validityInMilliseconds) {
        LocalDateTime now = LocalDateTime.now();

        return Date.from(now.atZone(ZoneId.systemDefault())
                .plus(validityInMilliseconds, ChronoUnit.MILLIS)
                .toInstant());
    }
}
