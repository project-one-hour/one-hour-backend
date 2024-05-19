package com.project1hour.api.core.domain.member.profileinfo;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    private static final Pattern KOREAN_NUMBER_REGX = Pattern.compile("^[가-힣\\d]+$");
    private static final Pattern KOREAN_ENGLISH_NUMBER_REGX = Pattern.compile("^(?=.*[a-zA-Z])[ㄱ-ㅎㅏ-ㅡ가-힣a-zA-Z\\d]+$");

    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 8;

    @Column(name = "nickname", unique = true, length = 10)
    private String value;

    public Nickname(final String value) {
        validate(value);
        this.value = value;
    }

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Nickname nickname = (Nickname) o;
        return Objects.equals(getValue(), nickname.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
