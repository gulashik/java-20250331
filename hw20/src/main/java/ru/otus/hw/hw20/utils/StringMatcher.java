package ru.otus.hw.hw20.utils;

public class StringMatcher {
    private final StringBuilder sb = new StringBuilder();
    private final int MAX_LENGTH;
    private final String PATTERN;

    public StringMatcher(String pattern) {
        this.PATTERN = pattern;
        MAX_LENGTH = pattern.length();
    }

    public boolean addAndMatch(char ch) {
        if (sb.length() >= MAX_LENGTH) {
            sb.deleteCharAt(0);
        }
        sb.append(ch);
        System.out.printf("sb = %s vs %s ", sb, PATTERN);
        return sb.toString().matches(PATTERN);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
