package com.project1hour.api.core.application.user.data;

import java.io.InputStream;

public interface ProfileImageInput {
    InputStream inputStream();

    boolean isPrimary();
}
