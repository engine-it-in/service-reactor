package ru.nikitinia.servicereactorapplication.exception;

public class ExternalServiceConnectionException extends RuntimeException {
    public ExternalServiceConnectionException(String message) {
        super(message);
    }
}
