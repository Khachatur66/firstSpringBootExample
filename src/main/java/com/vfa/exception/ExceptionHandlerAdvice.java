package com.vfa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException e) {
        return new ResponseEntity<>(createMessage(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<?> DuplicateDataException(DuplicateDataException e) {
        return new ResponseEntity<>(createMessage(e, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestException(BadRequestException e) {
        return new ResponseEntity<>(createMessage(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }


    private Map<String, Object> createMessage(Throwable e, HttpStatus status) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();

        errorResponse.put("statusCode", status.value());
        errorResponse.put("timestamp", new Date());
        errorResponse.put("error", e.getMessage());

        return errorResponse;
    }
}
