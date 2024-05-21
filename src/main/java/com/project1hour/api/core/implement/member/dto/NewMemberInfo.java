package com.project1hour.api.core.implement.member.dto;

import java.time.LocalDate;
import java.util.List;

public record NewMemberInfo(
        String nickname,
        String gender,
        LocalDate birthday,
        String mbti,
        List<String> interests,
        boolean isAllowingMarketingInfo
) {
}
