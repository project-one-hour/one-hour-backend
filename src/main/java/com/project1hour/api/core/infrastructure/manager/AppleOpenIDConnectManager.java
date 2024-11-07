package com.project1hour.api.core.infrastructure.manager;

import com.project1hour.api.core.application.user.imports.OpenIDConnectManager;
import com.project1hour.api.core.application.user.model.UserSocialInfo;
import com.project1hour.api.core.application.user.model.WebKey;
import com.project1hour.api.core.infrastructure.component.OAuthPublicKeyLocator;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleOpenIDConnectManager implements OpenIDConnectManager {

    private static final String PROVIDER_TYPE = "APPLE";

    private final String appleIssuer;
    private final String appleServiceId;

    public AppleOpenIDConnectManager(@Value("${oauth2.apple.service-id}") final String appleServiceId,
                                     @Value("${oauth2.apple.auth-domain}") final String appleIssuer) {
        this.appleIssuer = appleIssuer;
        this.appleServiceId = appleServiceId;
    }

    @Override
    public boolean isSupport(final String providerName) {
        return StringUtils.equalsIgnoreCase(providerName, PROVIDER_TYPE);
    }

    @Override
    public UserSocialInfo parseIdTokenByWebKeys(final String idToken, final List<WebKey> webKeys) {
        Claims claims = extractClaimsFromIdToken(idToken, webKeys);
        return new UserSocialInfo(claims.getSubject());
    }

    private Claims extractClaimsFromIdToken(final String idToken, final List<WebKey> webKeys) {
        try {
            return Jwts.parser()
                    .requireAudience(appleServiceId)
                    .requireIssuer(appleIssuer)
                    .keyLocator(new OAuthPublicKeyLocator(webKeys))
                    .build()
                    .parseSignedClaims(idToken)
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
}
