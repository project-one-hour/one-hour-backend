package com.project1hour.api.global.support;

import java.util.Optional;
import java.util.function.Function;

public class ValueObjectUtils {

    public static <A, B> B nullableValue(A nullableObject, Function<A, B> composeFunction) {
        return Optional.ofNullable(nullableObject).map(composeFunction).orElse(null);
    }
}
