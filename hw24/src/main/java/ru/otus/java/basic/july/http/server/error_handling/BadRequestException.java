package ru.otus.java.basic.july.http.server.error_handling;

public class BadRequestException extends RuntimeException {
    private String code;

    public String getCode() {
        return code;
    }

    public BadRequestException(String message, String code) {
        super(message);
        this.code = code;
    }
}
