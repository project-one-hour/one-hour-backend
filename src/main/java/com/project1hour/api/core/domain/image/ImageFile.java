package com.project1hour.api.core.domain.image;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public record ImageFile(String originalFileName, String contentType, String extension, InputStream imageInputStream) {

    private static final Pattern IMAGE_EXTENSION_REGX = Pattern.compile("^(png|jpeg|jpg|svg)$");

    public static ImageFile from(final MultipartFile multipartFile) {
        validateNullFile(multipartFile);
        validateEmptyFile(multipartFile);
        validateFileName(multipartFile);
        validateExtension(multipartFile);

        try {
            return new ImageFile(
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()),
                    multipartFile.getInputStream()
            );
        } catch (IOException e) {
            throw new BadRequestException("이미지를 읽을 수 없습니다.", ErrorCode.CAN_NOT_READ_IMAGE);
        }
    }

    private static void validateNullFile(final MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new BadRequestException("이미지로 NULL값이 들어올 수 없습니다.", ErrorCode.NULL_IMAGE);
        }
    }

    private static void validateEmptyFile(final MultipartFile multipartFile) {
        if (multipartFile.getSize() == 0) {
            throw new BadRequestException("이미지 파일이 비어있을 수 없습니다.", ErrorCode.EMPTY_IMAGE);
        }
    }

    private static void validateFileName(final MultipartFile multipartFile) {
        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
            throw new BadRequestException("이미지의 이름이 NULL이거나 비어있을 수 없습니다.", ErrorCode.INVALID_IMAGE_NAME);
        }
    }

    private static void validateExtension(final MultipartFile multipartFile) {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        Matcher matcher = IMAGE_EXTENSION_REGX.matcher(extension);

        String message = String.format("불가능한 이미지 확장자 입니다. extension = %s", extension);
        if (!matcher.matches()) {
            throw new BadRequestException(message, ErrorCode.INVALID_IMAGE_EXTENSION);
        }
    }

    public String randomName() {
        return UUID.randomUUID() + "." + extension;
    }
}
