package com.project1hour.api.core.presentation.filter;

import com.project1hour.api.core.application.image.exports.ImageCompressionFacade;
import com.project1hour.api.core.presentation.filter.ImageFilterRequest.WrappedPart;
import com.project1hour.api.core.presentation.filter.core.AnnotatedUrlMappingFilter;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageOptimizationFilter extends AnnotatedUrlMappingFilter<ImageOptimize> {

    private final ImageCompressionFacade imageCompressionFacade;
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
        return new WrappedPart(part, imageCompressionFacade::compressImage);
    }
}
