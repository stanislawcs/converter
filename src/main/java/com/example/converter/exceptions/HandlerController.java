package com.example.converter.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HandlerController {
//    @ExceptionHandler
//    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
//        log.error("RuntimeException");
//        ExceptionResponse response = new ExceptionResponse(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }


}
