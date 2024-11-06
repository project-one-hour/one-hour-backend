package com.project1hour.api.core.infrastructure.component;

import com.project1hour.api.core.application.user.model.WebKey;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.LocatorAdapter;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuthPublicKeyLocator extends LocatorAdapter<Key> {

    private static final int POSITIVE_SIG_NUM = 1;

    private final List<WebKey> webKeys;

    @Override
    protected Key locate(final JwsHeader header) {
        WebKey webKey = webKeys.stream()
                .filter(key -> key.algorithm().equals(header.getAlgorithm()))
                .filter(key -> key.keyId().equals(header.getKeyId()))
                .findFirst()
                .orElseThrow(() ->
                        new InfraStructureException("지정된 JWK를 찾을 수 없습니다.", ErrorCode.CAN_NOT_EXCHANGE_OAUTH_PROFILE)
                );
        return generatePublicKey(webKey);
    }

    private Key generatePublicKey(final WebKey webKey) {
        byte[] n = Base64.getUrlDecoder().decode(webKey.modulus());
        byte[] e = Base64.getUrlDecoder().decode(webKey.exponent());

        RSAPublicKeySpec publicKeySpec =
                new RSAPublicKeySpec(new BigInteger(POSITIVE_SIG_NUM, n), new BigInteger(POSITIVE_SIG_NUM, e));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(webKey.keyType());
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new InfraStructureException("지정된 JWK로 공개키를 생성할 수 없습니다.", ErrorCode.CAN_NOT_EXCHANGE_OAUTH_PROFILE);
        }
    }
}
