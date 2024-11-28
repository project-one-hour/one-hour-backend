package com.project1hour.api.core.infrastructure.adapter;

import com.project1hour.api.core.application.credit.imports.CreditCommandPort;
import com.project1hour.api.core.domain.credit.entity.Credit;
import com.project1hour.api.core.infrastructure.mapper.CreditMapper;
import com.project1hour.api.core.infrastructure.persistence.CreditEntity;
import com.project1hour.api.core.infrastructure.persistence.UserEntity;
import com.project1hour.api.core.infrastructure.repository.JpaCreditRepository;
import com.project1hour.api.core.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditPersistenceAdapter implements CreditCommandPort {

    private final CreditMapper creditMapper;
    private final JpaCreditRepository jpaCreditRepository;
    private final JpaUserRepository jpaUserRepository;

    @Override
    public Credit create(final Credit credit) {
        UserEntity userEntity = jpaUserRepository.getReferenceById(credit.getUserId().id());
        CreditEntity creditEntity = creditMapper.toEntity(credit).withUser(userEntity);
        CreditEntity createdCreditEntity = jpaCreditRepository.save(creditEntity);
        return creditMapper.toDomain(createdCreditEntity);
    }
}
