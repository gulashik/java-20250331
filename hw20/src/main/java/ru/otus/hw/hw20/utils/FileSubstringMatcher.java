package ru.otus.hw.hw20.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileSubstringMatcher {
    private final StringBuilder sb = new StringBuilder();
    private final int MAX_LENGTH;
    private final String PATTERN;

    public FileSubstringMatcher(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        this.PATTERN = pattern;
        MAX_LENGTH = pattern.length();
    }

    public long countSubstringMatchInFile(String fileName) {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            return bufferedReader.lines()
                .flatMapToInt(String::chars)
                .map(value -> addAndCount((char) value))
                .sum();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int addAndCount(char ch) {
        if (sb.length() >= MAX_LENGTH) {
            sb.deleteCharAt(0);
        }
        sb.append(ch);
        return sb.toString().matches(PATTERN) ? 1 : 0;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
