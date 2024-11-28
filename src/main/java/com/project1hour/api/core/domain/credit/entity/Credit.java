package com.project1hour.api.core.domain.credit.entity;

import com.project1hour.api.core.domain.AbstractDomainEntity;
import com.project1hour.api.core.domain.credit.value.CreditId;
import com.project1hour.api.core.domain.credit.value.EarnType;
import com.project1hour.api.core.domain.credit.value.Quantity;
import com.project1hour.api.core.domain.user.value.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Credit extends AbstractDomainEntity<CreditId> {

    private final CreditId id;

    private EarnType earnType;

    private Quantity quantity;

    private UserId userId;

    @Builder
    public Credit(final CreditId id, final EarnType earnType, final Quantity quantity, final UserId userId) {
        this.id = id;
        this.earnType = earnType;
        this.quantity = quantity;
        this.userId = userId;
    }

    public static Credit createCreditByEarnType(final UserId userId, final EarnType earnType) {
        return Credit.builder()
                .userId(userId)
                .earnType(earnType)
                .quantity(Quantity.fromEarnType(earnType))
                .build();
    }
}
