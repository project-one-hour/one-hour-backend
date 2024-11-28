package com.project1hour.api.global;

import com.project1hour.api.core.domain.AbstractDomainEntity;
import com.project1hour.api.core.domain.user.value.UserId;
import lombok.Getter;

@Getter
public class TestDomain extends AbstractDomainEntity<UserId> {

    private final UserId id;

    public TestDomain(Long id) {
        this.id = new UserId(id);
    }
}
