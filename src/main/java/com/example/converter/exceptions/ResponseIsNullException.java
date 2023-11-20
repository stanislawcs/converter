package com.example.converter.exceptions;

public class ResponseIsNullException extends RuntimeException{
    public ResponseIsNullException(String message){
        super(message);
    }
}
