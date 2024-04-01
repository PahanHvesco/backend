package com.myapp.backend.exception;

public class BadRequestErrorException extends RuntimeException {
    public BadRequestErrorException(final String message) {
        super(message);
    }
}
