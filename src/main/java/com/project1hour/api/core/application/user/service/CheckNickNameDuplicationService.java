package com.project1hour.api.core.application.user.service;

public interface CheckNickNameDuplicationService {

    boolean checkNickNameDuplication(Request request);

    record Request(String nickname) {
    }
}
