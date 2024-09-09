package com.project1hour.api.core.infrastructure.user.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1hour.api.core.application.user.manager.TokenAuthManager;
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
import java.util.Map;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthManager implements TokenAuthManager {

    private final static TypeReference<Map<String, Object>> CLAIMS_TYPE_REFERENCE = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;
    private final SecretKey signingKey;
    private final long accessTokenExpireMilliSecond;

    public JwtAuthManager(@Value("${jwt.token.secret-key}") final String signingKey,
                          @Value("${jwt.token.expire-length.access}") final long accessTokenExpireMilliSecond,
                          final ObjectMapper objectMapper) {
        byte[] keyBytes = signingKey.getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpireMilliSecond = accessTokenExpireMilliSecond;
        this.objectMapper = objectMapper;
    }

    @Override
    public String authorize(final UserDetail userDetail) {
        return Jwts.builder()
                .issuedAt(issuedNow())
                .expiration(expiredAt(accessTokenExpireMilliSecond))
                .signWith(signingKey)
                .claims(objectMapper.convertValue(userDetail, CLAIMS_TYPE_REFERENCE))
                .compact();
    }

    @Override
    public boolean isAuthenticated(final String token) {
        Claims claim = extractBody(token);
        return ObjectUtils.isNotEmpty(claim);
    }

    @Override
    public UserDetail getUserDetail(final String token) {
        return objectMapper.convertValue(extractBody(token), UserDetail.class);
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

    private Date expiredAt(final long validityInMilliseconds) {
        LocalDateTime now = LocalDateTime.now();

        return Date.from(now.atZone(ZoneId.systemDefault())
                .plus(validityInMilliseconds, ChronoUnit.MILLIS)
                .toInstant());
    }
}
