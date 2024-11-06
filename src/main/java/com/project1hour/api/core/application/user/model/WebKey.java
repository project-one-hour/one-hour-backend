package com.project1hour.api.core.application.user.model;

public interface WebKey {
    String keyId();

    String keyType();

    String algorithm();

    String use();

    String modulus();

    String exponent();
}
