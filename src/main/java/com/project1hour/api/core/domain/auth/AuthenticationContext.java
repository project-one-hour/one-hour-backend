package com.project1hour.api.core.domain.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationContext {

    private String principal;

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }

    public String getPrincipal() {
        return principal;
    }
}
