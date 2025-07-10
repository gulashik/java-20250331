package ru.gulash.server.exception;

/**
 * Исключение, выбрасываемое при получении команды выхода от клиента.
 */
public class ExitClientException extends RuntimeException{
    public ExitClientException(String message) {
        super(message);
    }
}
