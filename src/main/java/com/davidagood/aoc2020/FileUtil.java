package com.davidagood.aoc2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.Objects.isNull;

public class FileUtil {

    public static List<String> readFileLines(String resourceFileName) {
        URL resource = FileUtil.class.getClassLoader().getResource(resourceFileName);
        if (isNull(resource)) {
            throw new FileNotFoundException("File not found: " + resourceFileName);
        }
        try {
            return Files.readAllLines(Path.of(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to read file lines; Error: " + e.getMessage());
        }
    }

    public static String readFileAsString(String resourceFileName) {
        URL resource = FileUtil.class.getClassLoader().getResource(resourceFileName);
        if (isNull(resource)) {
            throw new FileNotFoundException("File not found: " + resourceFileName);
        }
        try {
            return Files.readString(Path.of(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to read file lines; Error: " + e.getMessage());
        }
    }

    public static class FileNotFoundException extends RuntimeException {
        public FileNotFoundException(String message) {
            super(message);
        }
    }

}
