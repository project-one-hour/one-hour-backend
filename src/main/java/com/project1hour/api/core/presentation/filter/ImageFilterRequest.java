package com.project1hour.api.core.presentation.filter;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import com.project1hour.api.global.support.IOUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class ImageFilterRequest extends HttpServletRequestWrapper {

    private final Function<Part, Part> compressPartProxy;
    private final Collection<Part> compressedParts;

    public ImageFilterRequest(final HttpServletRequest request,
                              final Function<Part, Part> compressPartProxy) {
        super(request);
        this.compressPartProxy = compressPartProxy;
        compressedParts = new ArrayList<>();
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        if (compressedParts.isEmpty()) {
            Collection<Part> mappedParts = super.getParts().stream().map(compressPartProxy).toList();
            compressedParts.addAll(mappedParts);
        }
        return Collections.unmodifiableCollection(compressedParts);
    }

    @Override
    public Part getPart(final String name) throws IOException, ServletException {
        return getParts().stream()
                .filter(part -> part.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * 요청의 헤더는 원본 파일의 헤더 값을 포함하고 있습니다!!
     */
    static class WrappedPart implements Part {

        private final Part delegatePart;
        private final Function<InputStream, InputStream> compressedImageProxy;

        public WrappedPart(final Part delegatePart, final Function<InputStream, InputStream> compressedImageProxy) {
            this.delegatePart = delegatePart;
            this.compressedImageProxy = compressedImageProxy;
        }

        public InputStream getInputStream() throws IOException {
            InputStream compressedImageInput = compressedImageProxy.apply(delegatePart.getInputStream());
            InputStream originalImageInput = delegatePart.getInputStream();
            if (originalImageInput.available() < compressedImageInput.available()) {
                IOUtils.closeQuietly(compressedImageInput);
                return originalImageInput;
            }

            IOUtils.closeQuietly(originalImageInput);
            return compressedImageInput;
        }

        @Override
        public long getSize() {
            try (InputStream inputStream = getInputStream()) {
                return inputStream.available();
            } catch (IOException e) {
                throw new InfraStructureException("이미지 크기를 가져오는 중 오류가 발생했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }

        @Override
        public String getContentType() {
            return delegatePart.getContentType();
        }

        @Override
        public String getName() {
            return delegatePart.getName();
        }

        @Override
        public String getSubmittedFileName() {
            return delegatePart.getSubmittedFileName();
        }

        @Override
        public void write(final String fileName) throws IOException {
            delegatePart.write(fileName);
        }

        @Override
        public void delete() throws IOException {
            delegatePart.delete();
        }

        @Override
        public String getHeader(final String name) {
            return delegatePart.getHeader(name);
        }

        @Override
        public Collection<String> getHeaders(final String name) {
            return delegatePart.getHeaders(name);
        }

        @Override
        public Collection<String> getHeaderNames() {
            return delegatePart.getHeaderNames();
        }
    }
}
