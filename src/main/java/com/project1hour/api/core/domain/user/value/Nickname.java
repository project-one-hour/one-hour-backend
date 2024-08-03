package com.project1hour.api.core.domain.user.value;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public record Nickname(
        @Column(name = "nickname", unique = true, length = 10)
        String value
) {

    private static final Pattern KOREAN_NUMBER_REGX = Pattern.compile("^[가-힣\\d]+$");
    private static final Pattern KOREAN_ENGLISH_NUMBER_REGX = Pattern.compile("^(?=.*[a-zA-Z])[ㄱ-ㅎㅏ-ㅡ가-힣a-zA-Z\\d]+$");

    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 8;

    public Nickname {
        validate(value);
    }

    /**
     * Policy : 한글, 영어, 숫자 조합만 가능 / 자음, 모음 X / 2 ~ 8 자
     */
    private void validate(final String value) {
        validateLength(value);
        validateFormat(value);
    }

    private static void validateLength(final String value) {
        if (value.length() < NAME_MIN_LENGTH || value.length() > NAME_MAX_LENGTH) {
            String message = String.format("닉네임은 %d글자 이상 %d글자 이하여야 합니다. 현재길이 = %d", NAME_MIN_LENGTH, NAME_MAX_LENGTH,
                    value.length());
            throw new BadRequestException(message, ErrorCode.INVALID_NICKNAME_LENGTH);
        }
    }

    private void validateFormat(final String value) {
        Matcher koreanNumberMatcher = KOREAN_NUMBER_REGX.matcher(value);
        Matcher koreanEnglishNumberMatcher = KOREAN_ENGLISH_NUMBER_REGX.matcher(value);

        if (!koreanNumberMatcher.matches() && !koreanEnglishNumberMatcher.matches()) {
            String message = String.format("닉네임의 형식이 잘못됐습니다. 현재닉네임 = %s", value);
            throw new BadRequestException(message, ErrorCode.INVALID_NICKNAME_FORMAT);
        }
    }
}
