package com.test.prismocodeassessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTransactionException extends RuntimeException {
    private String message;

    public InvalidTransactionException(String message) {
        super(message);
        this.message = message;
    }
}
