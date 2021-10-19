package com.sparta.kerly_clone.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}
