package com.sparta.kerly_clone.exception;

public class JwtTokenExpiredException extends RuntimeException{
    public JwtTokenExpiredException(String msg) {
        super(msg);
    }
}
