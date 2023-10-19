package ru.geraskindenis.exceptions;

public class PersonalAccountServiceException extends RuntimeException {
    public PersonalAccountServiceException(String message) {
        super(message);
    }

    public PersonalAccountServiceException(Throwable cause) {
        super(cause);
    }

    public PersonalAccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
