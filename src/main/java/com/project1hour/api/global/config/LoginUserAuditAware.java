package com.project1hour.api.global.config;

import com.project1hour.api.core.domain.auth.AuthenticationContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserAuditAware implements AuditorAware<Long> {

    private final AuthenticationContext authenticationContext;

    @Override
    public Optional<Long> getCurrentAuditor() {
        String principal = authenticationContext.getPrincipal();

        return Optional.ofNullable(principal)
                .map(Long::parseLong);
    }
}
