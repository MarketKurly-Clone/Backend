package com.sparta.kerly_clone.exception;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String msg) {
        super(msg);
    }
}
