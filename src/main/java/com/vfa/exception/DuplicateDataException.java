package com.vfa.exception;

public class DuplicateDataException extends Exception{

    public DuplicateDataException() {
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
