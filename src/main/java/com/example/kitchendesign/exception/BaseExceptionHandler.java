package com.example.kitchendesign.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {  //??????????????

        Map<String, String> errors = new HashMap<>();
        String bodyOfResponse = "VALIDATION_FAILED";

        ex.getBindingResult().getFieldErrors().forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()),
                                error.getDefaultMessage()));
                    } else {
                        errors.put(error.getField(), error.getDefaultMessage());

                    }
                }
        );

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);          //??????????????
    }
}
