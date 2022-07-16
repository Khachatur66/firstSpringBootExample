package com.vfa.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends Exception {

    private HttpStatus status;

    public AccessDeniedException() {
    }

    public AccessDeniedException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
