package com.project1hour.api.core.infrastructure.restclient;

import com.project1hour.api.core.application.image.imports.ImageClient;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!prod")
public class NonFunctionalImageClient implements ImageClient {

    @PostConstruct
    public void init() {
        log.info("개발 서버는 해당 컴포넌트로 대체합니다!! [{}]", this.getClass().getSimpleName());
    }

    @Override
    public String uploadImage(final InputStream image, final String imageExtension) {
        log.info("업로드 이미지 스트림 : {}", image);
        log.info("이미지 확장자 : {}", imageExtension);
        return UUID.randomUUID() + "." + imageExtension;
    }
}
