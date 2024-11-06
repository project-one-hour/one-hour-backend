package com.project1hour.api.core.application.user.exports;

public interface CheckNicknameDuplicationService {

    Response checkNickNameDuplication(Request request);

    record Request(String nickname) {
    }

    record Response(boolean isDuplicate) {
    }
}
