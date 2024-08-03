package com.project1hour.api.global.support;

import io.hypersistence.utils.hibernate.id.Tsid.FactorySupplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdGenerator {

    public static Long generateId() {
        return FactorySupplier.INSTANCE.get().generate().toLong();
    }
}
