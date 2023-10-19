package ru.geraskindenis.exceptions;

public class TransactionServiceException extends RuntimeException {

    public TransactionServiceException(String message) {
        super(message);
    }

    public TransactionServiceException(Throwable cause) {
        super(cause);
    }
}
