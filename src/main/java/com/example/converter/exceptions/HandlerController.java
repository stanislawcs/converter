package com.example.converter.exceptions;

import com.example.converter.models.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class HandlerController {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleInternalServerErrorException(HttpClientErrorException.NotFound e) {

        log.error("HttpClientErrorException.NotFound: check currency abbreviation");
        ExceptionResponse response = new ExceptionResponse("Check url or currency abbreviation");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {

        log.error("MissingServletRequestParameterException");
        ExceptionResponse response = new ExceptionResponse("Check the parameters name");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException");
        ExceptionResponse response = new ExceptionResponse("Check the api url, or parameters");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException: ");
        ExceptionResponse response = new ExceptionResponse("Invalid date format, check date parameter");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
