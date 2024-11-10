package com.project1hour.api.core.infrastructure.restclient;

import static org.bouncycastle.openssl.PEMParser.TYPE_PRIVATE_KEY;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import com.project1hour.api.core.application.user.imports.OauthRestClient;
import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.application.user.model.WebKey;
import com.project1hour.api.core.infrastructure.restclient.model.AppleJWKsResponseBody;
import com.project1hour.api.core.infrastructure.restclient.model.AppleTokensRequestBody;
import com.project1hour.api.core.infrastructure.restclient.model.AppleTokensResponseBody;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.Security;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class AppleOauthRestClient implements OauthRestClient {

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static final String PROVIDER_TYPE = "APPLE";
    private static final long MONTHS_TO_ADD = 1;

    private final String requestTokensUrl;
    private final String requestPublicKeyUrl;
    private final String redirectUrl;
    private final String serviceId;
    private final String teamId;
    private final String keyId;
    private final String audience;

    private final String authKeyPath;
    private final JcaPEMKeyConverter converter;

    private final RestClient restClient;

    public AppleOauthRestClient(@Value("${oauth2.apple.url.tokens}") final String requestTokensUrl,
                                @Value("${oauth2.apple.url.public-key}") final String requestPublicKeyUrl,
                                @Value("${oauth2.apple.url.callback}") final String redirectUrl,
                                @Value("${oauth2.apple.service-id}") final String serviceId,
                                @Value("${oauth2.apple.team-id}") final String teamId,
                                @Value("${oauth2.apple.key-id}") final String keyId,
                                @Value("${oauth2.apple.auth-domain}") final String audience,
                                @Value("${oauth2.apple.auth-key-path}") final String authKeyPath,
                                final RestClient.Builder restClientBuilder) {
        this.requestTokensUrl = requestTokensUrl;
        this.requestPublicKeyUrl = requestPublicKeyUrl;
        this.redirectUrl = redirectUrl;
        this.serviceId = serviceId;
        this.teamId = teamId;
        this.keyId = keyId;
        this.audience = audience;
        this.authKeyPath = authKeyPath;
        this.converter = new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME);
        this.restClient = restClientBuilder.build();
    }

    @Override
    public boolean isSupport(final String providerName) {
        return StringUtils.equalsIgnoreCase(providerName, PROVIDER_TYPE);
    }

    @Override
    public TokenPackage requestTokenPackageByAuthorizationCode(final String authorizationCode) {
        return restClient.post()
                .uri(requestTokensUrl)
                .contentType(APPLICATION_FORM_URLENCODED)
                .body(createAppleTokensRequestForm(authorizationCode))
                .retrieve()
                .body(AppleTokensResponseBody.class);
    }

    @Override
    public List<WebKey> requestWebKeys() {
        AppleJWKsResponseBody response = restClient.get()
                .uri(requestPublicKeyUrl)
                .retrieve()
                .body(AppleJWKsResponseBody.class);
        return Collections.unmodifiableList(response.keys());
    }

    private MultiValueMap<String, String> createAppleTokensRequestForm(final String authorizationCode) {
        return AppleTokensRequestBody.builder()
                .clientId(serviceId)
                .redirectUri(redirectUrl)
                .code(authorizationCode)
                .clientSecret(createClientSecretToken())
                .build()
                .toMultiValueMap();
    }

    private String createClientSecretToken() {
        return Jwts.builder()
                .header()
                .keyId(keyId)
                .and()

                .claims()
                .audience().add(audience).and()
                .issuer(teamId)
                .subject(serviceId)
                .issuedAt(issuedNow())
                .expiration(expiration())
                .and()

                .signWith(generatePrivateKey(), SIG.ES256)
                .compact();
    }

    private PrivateKey generatePrivateKey() {
        try {
            Path privateKeyPath = Path.of(new ClassPathResource(authKeyPath).getURI());
            String privateKeyContent = Files.readAllLines(privateKeyPath).stream()
                    .filter(this::containsPrivateKeyContent)
                    .collect(Collectors.joining());

            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyContent);
            return converter.getPrivateKey(PrivateKeyInfo.getInstance(privateKeyBytes));
        } catch (Exception e) {
            log.error("스택 트레이스 : {}", ExceptionUtils.getStackTrace(e));
            throw new InfraStructureException("비밀 키를 생성할 수 없습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean containsPrivateKeyContent(final String content) {
        return !StringUtils.contains(content, TYPE_PRIVATE_KEY);
    }

    private Date issuedNow() {
        Instant instant = Instant.now()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    private Date expiration() {
        Instant instant = LocalDateTime.now()
                .plusMonths(MONTHS_TO_ADD)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }
}
