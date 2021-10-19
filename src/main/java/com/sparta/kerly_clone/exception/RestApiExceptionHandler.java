package com.sparta.kerly_clone.exception;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> RestApiExceptionHandle(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }
}