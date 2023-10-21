package ru.geraskindenis.exceptions;

public class UserEventServiceException extends RuntimeException {

    public UserEventServiceException() {
    }

    public UserEventServiceException(String message) {
        super(message);
    }

    public UserEventServiceException(Throwable cause) {
        super(cause);
    }

    public UserEventServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
