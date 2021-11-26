package com.example.sportmate.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
