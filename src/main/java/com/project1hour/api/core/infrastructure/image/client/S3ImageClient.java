package com.project1hour.api.core.infrastructure.image.client;

import com.project1hour.api.core.application.image.api.ImageClient;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.InputStream;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3ImageClient implements ImageClient {

    private final String bucket;
    private final String cdnUrl;
    private final String savePath;
    private final S3Template s3Template;

    public S3ImageClient(@Value("${spring.cloud.aws.s3.bucket}") final String bucket,
                         @Value("${spring.cloud.aws.s3.cdn-url}") final String cdnUrl,
                         @Value("${spring.cloud.aws.s3.save-path}") final String savePath,
                         final S3Template s3Template) {
        this.bucket = bucket;
        this.cdnUrl = cdnUrl;
        this.savePath = savePath;
        this.s3Template = s3Template;
    }

    @Override
    public String uploadImage(final InputStream image) {
        try {
            S3Resource resource = s3Template.upload(bucket, generateFilePath(""), image);
            return cdnUrl + resource.getFilename();
        } catch (S3Exception e) {
            throw new InfraStructureException("S3 버킷에 이미지를 업로드할 수 없습니다.", ErrorCode.CAN_NOT_UPLOAD_IMAGE_TO_S3);
        }
    }

    private String generateFilePath(String extension) {
        String randomFileName = StringUtils.join(UUID.randomUUID(), ".", extension);
        return StringUtils.join(savePath, randomFileName);
    }
}
