package com.sparta.kerly_clone.exception;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(value = {DuplicateUserException.class})
    public ResponseEntity<Object> DuplicateUserExceptionHandler(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = {EmailFormException.class})
    public ResponseEntity<Object> EmailFormExceptionHandler(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = {LengthException.class})
    public ResponseEntity<Object> LengthExceptionHandler(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = {EmptyException.class})
    public ResponseEntity<Object> EmptyExceptionHandler(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = {JwtTokenExpiredException.class})
    public ResponseEntity<Object> JwtTokenExpiredExceptionHandler(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = {TokenNullException.class})
    public ResponseEntity<Object> TokenNullExpiredExceptionHandler(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    public ResponseEntity<Object> InvalidTokenExceptionHandler(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = {UnauthenticatedException.class})
    public ResponseEntity<Object> UnauthenticatedExceptionHandler(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage(),"");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }
}
