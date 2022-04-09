package com.example.sportmate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ExternalException extends RuntimeException{
    public ExternalException(final String message){
        super(message);
    }
}
