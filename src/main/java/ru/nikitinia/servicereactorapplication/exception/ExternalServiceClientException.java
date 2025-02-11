package ru.nikitinia.servicereactorapplication.exception;

public class ExternalServiceClientException extends RuntimeException {
    public ExternalServiceClientException(String message) {
        super(message);
    }
}
