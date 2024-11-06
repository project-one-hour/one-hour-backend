package com.project1hour.api.core.infrastructure.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1hour.api.core.application.user.imports.TokenAuthManager;
import com.project1hour.api.core.application.user.model.UserDetail;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenAuthManager implements TokenAuthManager {

    private final static TypeReference<Map<String, Object>> CLAIMS_TYPE_REFERENCE = new TypeReference<>() {
    };

    private final SecretKey accessTokenSecretKey;
    private final SecretKey refreshTokenSecretKey;
    private final long accessTokenExpiryMilliSecond;
    private final long refreshTokenExpiryMilliSecond;

    private final ObjectMapper objectMapper;

    public JwtTokenAuthManager(@Value("${jwt.access-token.secret-key}") final String accessTokenSecretKey,
                               @Value("${jwt.refresh-token.secret-key}") final String refreshTokenSecretKey,
                               @Value("${jwt.access-token.expire-length}") final long accessTokenExpireMilliSecond,
                               @Value("${jwt.refresh-token.expire-length}") final long refreshTokenExpireMilliSecond,
                               final ObjectMapper objectMapper) {
        this.accessTokenSecretKey = Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes(StandardCharsets.UTF_8));
        this.refreshTokenSecretKey = Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiryMilliSecond = accessTokenExpireMilliSecond;
        this.refreshTokenExpiryMilliSecond = refreshTokenExpireMilliSecond;
        this.objectMapper = objectMapper;
    }

    @Override
    public String createAccessToken(final UserDetail userDetail) {
        return Jwts.builder()
                .issuedAt(issuedNow())
                .expiration(expiredAt(accessTokenExpiryMilliSecond))
                .claims(objectMapper.convertValue(userDetail, CLAIMS_TYPE_REFERENCE))
                .signWith(accessTokenSecretKey)
                .compact();
    }

    @Override
    public String createRefreshToken(final Long userId) {
        return Jwts.builder()
                .issuedAt(issuedNow())
                .expiration(expiredAt(refreshTokenExpiryMilliSecond))
                .subject(userId.toString())
                .signWith(refreshTokenSecretKey)
                .compact();
    }

    @Override
    public UserDetail getUserDetail(final String accessToken) {
        Claims claims = extractPayload(accessToken, accessTokenSecretKey);
        return objectMapper.convertValue(claims, UserDetail.class);
    }

    private Claims extractPayload(final String token, final SecretKey secretKey) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
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
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private Date expiredAt(final long validityInMilliseconds) {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault())
                .plus(validityInMilliseconds, ChronoUnit.MILLIS)
                .toInstant());
    }
}
