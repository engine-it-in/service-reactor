package ru.nikitinia.servicereactorapplication.exception;

public class ExternalServiceServerException extends RuntimeException{
    public ExternalServiceServerException(String message) {
        super(message);
    }
}
