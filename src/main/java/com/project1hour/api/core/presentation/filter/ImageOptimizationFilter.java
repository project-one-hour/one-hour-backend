package com.project1hour.api.core.presentation.filter;

import com.project1hour.api.core.application.image.manager.ImageDetailManager;
import com.project1hour.api.core.application.image.manager.ImageExpressionManager;
import com.project1hour.api.core.application.image.manager.ImageExpressionManager.ImageEditOptions;
import com.project1hour.api.core.application.image.manager.ImageValidationManager;
import com.project1hour.api.core.application.image.manager.ImageValidationManager.ImageResource;
import com.project1hour.api.core.presentation.filter.ImageFilterRequest.WrappedPart;
import com.project1hour.api.core.presentation.filter.core.AnnotatedUrlMappingFilter;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import com.project1hour.api.global.support.IOUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageOptimizationFilter extends AnnotatedUrlMappingFilter<ImageOptimize> {

    private final ImageExpressionManager imageExpressionManager;
    private final ImageValidationManager imageValidationManager;
    private final ImageDetailManager imageDetailManager;

    private final MultipartResolver multipartResolver;

    @Override
    protected void doProcessFilter(final HttpServletRequest request, final HttpServletResponse response,
                                   final FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest processedRequest = getMultipartRequest(request)
                .orElseThrow(() -> new BadRequestException("요청이 멀티파트 형식이 아닙니다", ErrorCode.INVALID_MULTIPART_REQUEST));

        try {
            filterChain.doFilter(processedRequest, response);
        } finally {
            if (processedRequest instanceof MultipartHttpServletRequest multipartRequest) {
                multipartResolver.cleanupMultipart(multipartRequest);
            }
        }
    }

    private Optional<MultipartHttpServletRequest> getMultipartRequest(final HttpServletRequest request) {
        if (multipartResolver.isMultipart(request)) {
            var optimizedRequest = new ImageFilterRequest(request, this::optimizeImagePart);
            return Optional.of(multipartResolver.resolveMultipart(optimizedRequest));
        }

        return Optional.empty();
    }

    private Part optimizeImagePart(final Part part) {
        try {
            InputStream validationInputStream = part.getInputStream();
            ImageResource imageResource = new ImageResource(validationInputStream, part.getSize());
            if (!imageValidationManager.validateImage(imageResource)) {
                IOUtils.closeQuietly(validationInputStream);
                return part;
            }

            InputStream rotationInputStream = part.getInputStream();
            int imageRotation = imageDetailManager.getImageRotation(rotationInputStream);
            IOUtils.closeQuietly(rotationInputStream);

            return new WrappedPart(part, inputStream -> compressImage(imageRotation, inputStream));
        } catch (IOException e) {
            log.error("이미지 압축 중 I/O 오류 발생: {}", ExceptionUtils.getStackTrace(e));
            throw new InfraStructureException("이미지 처리 중 오류가 발생했습니다!", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private InputStream compressImage(final int imageRotation, final InputStream inputStream) {
        return imageExpressionManager.optimizeImage(ImageEditOptions.withDefaults(imageRotation, inputStream));
    }
}
