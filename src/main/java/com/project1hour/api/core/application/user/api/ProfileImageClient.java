package com.project1hour.api.core.application.user.api;

import java.io.InputStream;
import java.util.List;

public interface ProfileImageClient {

    boolean hasInvalidImages(List<InputStream> inputStreams);
}
