package com.project1hour.api.core.infrastructure.user.client;

import com.project1hour.api.core.application.user.api.ProfileImageClient;
import java.io.InputStream;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProfileImageServiceClient implements ProfileImageClient {

    /**
     * TODO : 이미지 도메인 정의 시 같이 정의해야함
     */
    @Override
    public boolean hasInvalidImages(final List<InputStream> inputStreams) {
        return true;
    }
}
