package ru.geraskindenis.exceptions;

public class TransactionServiceException extends RuntimeException {
    public TransactionServiceException() {
    }

    public TransactionServiceException(String message) {
        super(message);
    }

    public TransactionServiceException(Throwable cause) {
        super(cause);
    }

    public TransactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
