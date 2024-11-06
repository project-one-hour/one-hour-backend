package com.project1hour.api.core.application.user.model.dto;

import com.project1hour.api.core.application.user.model.dto.OauthInfoDTO.Pending;
import com.project1hour.api.core.application.user.model.dto.OauthInfoDTO.Verify;
import com.project1hour.api.core.domain.user.entity.User;

public sealed interface OauthInfoDTO permits Pending, Verify {

    record Pending(Long oauthInfoId) implements OauthInfoDTO {
    }

    record Verify(User user) implements OauthInfoDTO {
    }
}
