package com.project1hour.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.nio.charset.StandardCharsets;
import org.springframework.mock.web.MockMultipartFile;

public class DummyDataGenerator {

    private DummyDataGenerator() {
    }

    public static byte[] dummyBytes() {
        return "Hello World".getBytes(StandardCharsets.UTF_8);
    }

    public static MockMultipartFile dummyImageMultipartFile(final String name) {
        return new MockMultipartFile(name, "mock-image.jpeg", IMAGE_JPEG_VALUE, dummyBytes());
    }

    public static MockMultipartFile jsonMultipartFile(final String name, final String json) {
        return new MockMultipartFile(name, null, APPLICATION_JSON_VALUE, json.getBytes(StandardCharsets.UTF_8));
    }
}
