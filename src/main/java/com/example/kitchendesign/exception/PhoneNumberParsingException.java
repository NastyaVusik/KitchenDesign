package com.example.kitchendesign.exception;

public class PhoneNumberParsingException extends RuntimeException {

    public PhoneNumberParsingException(String message) {
        super(message);
    }

    public PhoneNumberParsingException(String message, Exception e) {
        super(message);
    }

}
