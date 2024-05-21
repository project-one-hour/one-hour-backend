package com.project1hour.api.global.support;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;

public abstract class JwtTokenExtractor {

    private static final Pattern JWT_TOKEN_REGX = Pattern.compile(
            "^Bearer \\b([A-Za-z\\d\\-_]*\\.[A-Za-z\\d\\-_]*\\.[A-Za-z\\d\\-_]*)$");
    private static final int JWT_TOKEN_INDEX = 1;

    public static Optional<String> extractToken(final HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isEmpty(header) || isNotValidJwtToken(header)) {
            return Optional.empty();
        }

        Matcher matcher = JWT_TOKEN_REGX.matcher(header);
        if (matcher.find()) {
            return Optional.of(matcher.group(JWT_TOKEN_INDEX));
        }
        return Optional.empty();
    }

    private static boolean isNotValidJwtToken(final String header) {
        return !JWT_TOKEN_REGX.matcher(header)
                .matches();
    }
}
