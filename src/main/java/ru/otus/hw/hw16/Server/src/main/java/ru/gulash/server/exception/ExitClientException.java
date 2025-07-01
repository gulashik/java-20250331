package ru.gulash.server.exception;

public class ExitClientException extends RuntimeException{
    public ExitClientException(String message) {
        super(message);
    }
}
