package com.project1hour.api.core.infrastructure.image.manager;

import com.project1hour.api.core.application.image.manager.ImageExpressionManager;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.util.exif.Orientation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JPEGImageExpressionManager implements ImageExpressionManager {

    private static final String IMAGE_EXTENSION = "jpeg";

    private static final int MINIMUM_WIDTH = 100;
    private static final int MINIMUM_HEIGHT = 100;

    public static final int ROTATE_ANGLE_180 = 180;
    public static final int ROTATE_ANGLE_90 = 90;
    public static final int ROTATE_ANGLE_270 = -90;
    public static final int ROTATE_ANGLE_NONE = 0;

    @Override
    public InputStream optimizeImage(final ImageEditOptions options) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            createThumbnailBuilder(options)
                    .useExifOrientation(false)
                    .rotate(rotateImageAngle(options.rotation()))
                    .imageType(BufferedImage.TYPE_INT_RGB)
                    .outputFormat(IMAGE_EXTENSION)
                    .toOutputStream(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("이미지 압축 과정에서 I/O 오류 발생: {}", ExceptionUtils.getStackTrace(e));
            throw new InfraStructureException("이미지 압축 과정에서 오류가 발생했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getImageExtension() {
        return IMAGE_EXTENSION;
    }

    private Thumbnails.Builder<?> createThumbnailBuilder(final ImageEditOptions options) throws IOException {
        if (options.width() < MINIMUM_WIDTH || options.height() < MINIMUM_HEIGHT) {
            BufferedImage image = readImage(options.imageInput()).orElseThrow(() ->
                    new InfraStructureException("이미지 형식이 올바르지 않습니다.", ErrorCode.CAN_NOT_READ_IMAGE));

            return Thumbnails.of(image)
                    .size(image.getWidth(), image.getHeight());
        }

        return Thumbnails.of(options.imageInput())
                .size(options.width(), options.height());
    }

    private Optional<BufferedImage> readImage(final InputStream inputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return Optional.ofNullable(bufferedImage);
    }

    private double rotateImageAngle(final int orientation) {
        return switch (Orientation.typeOf(orientation)) {
            case BOTTOM_RIGHT, BOTTOM_LEFT -> ROTATE_ANGLE_180;
            case LEFT_TOP, RIGHT_TOP -> ROTATE_ANGLE_90;
            case RIGHT_BOTTOM, LEFT_BOTTOM -> ROTATE_ANGLE_270;
            default -> ROTATE_ANGLE_NONE;
        };
    }
}