package com.project1hour.api.core.application.user.service;

public interface CheckNicknameDuplicationService {

    Response checkNickNameDuplication(Request request);

    record Request(String nickname) {
    }

    record Response(boolean isDuplicate) {
    }
}
