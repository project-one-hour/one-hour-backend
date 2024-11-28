package com.project1hour.api.core.infrastructure.mapper;

import static com.project1hour.api.global.support.ValueObjectUtils.nullableValue;

import com.project1hour.api.core.domain.credit.entity.Credit;
import com.project1hour.api.core.domain.credit.value.CreditId;
import com.project1hour.api.core.domain.credit.value.EarnType;
import com.project1hour.api.core.domain.credit.value.Quantity;
import com.project1hour.api.core.domain.user.value.UserId;
import com.project1hour.api.core.infrastructure.persistence.CreditEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditMapper {

    public CreditEntity toEntity(final Credit credit) {
        return CreditEntity.builder()
                .id(nullableValue(credit.getId(), CreditId::id))
                .quantity(credit.getQuantity().value())
                .earnType(credit.getEarnType().name())
                .build();
    }

    public Credit toDomain(final CreditEntity creditEntity) {
        return Credit.builder()
                .id(new CreditId(creditEntity.getId()))
                .earnType(EarnType.find(creditEntity.getEarnType()))
                .quantity(new Quantity(creditEntity.getQuantity()))
                .userId(new UserId(creditEntity.getUser().getId()))
                .build();
    }
}
