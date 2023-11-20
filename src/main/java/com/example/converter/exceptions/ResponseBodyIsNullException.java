package com.example.converter.exceptions;

public class ResponseBodyIsNullException extends RuntimeException {
    public ResponseBodyIsNullException(String message) {
        super(message);
    }
}
