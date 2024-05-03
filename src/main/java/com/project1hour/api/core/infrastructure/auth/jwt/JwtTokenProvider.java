package com.project1hour.api.core.infrastructure.auth.jwt;

import com.project1hour.api.core.domain.auth.Authority;
import com.project1hour.api.core.domain.auth.TokenProvider;
import com.project1hour.api.core.exception.auth.ExpiredTokenException;
import com.project1hour.api.core.exception.auth.InvalidTokenSignatureException;
import com.project1hour.api.core.exception.auth.MalformedTokenException;
import com.project1hour.api.core.exception.auth.UnsupportedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
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

    private static final String AUTHORIZATION_ID = "id";
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

    // TODO validate, paring 로직 간소화
    public Long getParsedAuthInfo(final String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token);

            return (Long) claimsJws.getPayload()
                    .get(AUTHORIZATION_ID);
        } catch (ExpiredJwtException e) {
            return (Long) e.getClaims()
                    .get(AUTHORIZATION_ID);
        }
    }

    public void validateToken(final String token) {
        try {
            Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (SignatureException e) {
            log.info("Invalid JWT signature");
            throw new InvalidTokenSignatureException();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
            throw new UnsupportedTokenException();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
            throw new ExpiredTokenException();
        } catch (MalformedJwtException e) {
            log.info("Malformed JWT token");
            throw new MalformedTokenException();
        }
    }
}