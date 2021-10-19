package com.sparta.kerly_clone.exception;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsernameNotFound {
    public ResponseEntity<Object> handleApiRequestException(RuntimeException e) {
        ResponseDto restApiException = new ResponseDto("failed", e.getMessage(), "");

        return new ResponseEntity<>(restApiException, HttpStatus.OK);
    }
}
