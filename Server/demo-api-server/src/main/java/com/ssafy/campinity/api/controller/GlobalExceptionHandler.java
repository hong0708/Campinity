package com.ssafy.campinity.api.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.campinity.api.dto.ErrorMessage;
import com.ssafy.campinity.api.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handle(BadRequestException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> handle(NoSuchElementException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorMessage> handle(IOException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorMessage> handle(JsonProcessingException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handle(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
