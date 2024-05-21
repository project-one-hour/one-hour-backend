package com.project1hour.api.core.presentation.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project1hour.api.core.implement.member.dto.NewMemberInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record SignUpRequest(
        @NotBlank(message = "닉네임은 반드시 입력해야 합니다.")
        String nickname,

        @NotBlank(message = "성별은 반드시 입력해야 합니다.")
        String gender,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthday,

        @NotBlank(message = "MBTI는 반드시 입력해야 합니다.")
        String mbti,

        @Size(min = 5, max = 5, message = "관심사는 5개만 입력해야 합니다.")
        List<String> interests,

        @NotNull(message = "마케팅 수신 정보는 반드시 입력해야 합니다.")
        Boolean isAllowingMarketingInfo
) {
    public NewMemberInfo toNewMemberInfo() {
        return new NewMemberInfo(nickname, gender, birthday, mbti, interests, isAllowingMarketingInfo);
    }
}
