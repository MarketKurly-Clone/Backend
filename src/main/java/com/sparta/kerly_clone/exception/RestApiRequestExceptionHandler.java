package com.sparta.kerly_clone.exception;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class RestApiRequestExceptionHandler {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleApiRequestException(MethodArgumentNotValidException ex) {
        ResponseDto restApiException = new ResponseDto("failed", Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),"");

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }
}