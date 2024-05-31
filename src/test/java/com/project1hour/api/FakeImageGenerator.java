package com.project1hour.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FakeImageGenerator {

    public static File createFakeImage() throws IOException {
        File fakeImage = File.createTempFile("image", ".jpg");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fakeImage))) {
            writer.write("Dummy Value");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fakeImage;
    }
}
