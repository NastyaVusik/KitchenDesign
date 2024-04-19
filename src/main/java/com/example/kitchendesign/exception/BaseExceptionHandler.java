package com.example.kitchendesign.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;


@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

//    Invalidate registration data.
//    User try to register with occupied data
    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<?> forbiddenDataException(HttpClientErrorException exception){

        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }


    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> notFoundException(NotFoundException exception) {

        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }


//    Exception for parsing phone number with Google libphonenumber
    @ExceptionHandler(value = {PhoneNumberParsingException.class})
    public ResponseEntity<?> parsingException(PhoneNumberParsingException exception) {

        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }
}
