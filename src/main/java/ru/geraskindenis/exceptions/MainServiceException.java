package ru.geraskindenis.exceptions;

public class MainServiceException extends RuntimeException {
    public MainServiceException() {
    }

    public MainServiceException(String message) {
        super(message);
    }

    public MainServiceException(Throwable cause) {
        super(cause);
    }

    public MainServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
