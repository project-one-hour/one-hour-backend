package com.project1hour.api.core.infrastructure.image;

import com.project1hour.api.core.domain.image.ImageFile;
import com.project1hour.api.core.domain.image.ImageUploader;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3ImageUploader implements ImageUploader {

    private final String bucket;
    private final String cdnUrl;
    private final String savePath;
    private final S3Template s3Template;

    public S3ImageUploader(@Value("${spring.cloud.aws.s3.bucket}") final String bucket,
                           @Value("${spring.cloud.aws.s3.cdn-url}") final String cdnUrl,
                           @Value("${spring.cloud.aws.s3.save-path}") final String savePath,
                           final S3Template s3Template) {
        this.bucket = bucket;
        this.cdnUrl = cdnUrl;
        this.savePath = savePath;
        this.s3Template = s3Template;
    }

    @Override
    public String upload(final ImageFile imageFile) {
        try {
            S3Resource resource = s3Template.upload(bucket, savePath + imageFile.randomName(),
                    imageFile.getImageInputStream());

            return cdnUrl + resource.getFilename();
        } catch (S3Exception e) {
            throw new InfraStructureException("S3 버킷에 이미지를 업로드할 수 없습니다.", ErrorCode.CAN_NOT_UPLOAD_IMAGE_TO_S3);
        }
    }
}
