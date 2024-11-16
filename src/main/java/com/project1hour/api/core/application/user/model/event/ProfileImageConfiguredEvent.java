package com.project1hour.api.core.application.user.model.event;

import java.io.InputStream;

public record ProfileImageConfiguredEvent(Long imageId, InputStream imageInput) {

}
