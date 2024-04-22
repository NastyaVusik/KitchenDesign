package com.example.kitchendesign.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

public class PhoneNumberParsingException extends HttpClientErrorException {

    public PhoneNumberParsingException(HttpStatusCode statusCode) {
        super(statusCode);
    }

    public PhoneNumberParsingException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public PhoneNumberParsingException(HttpStatusCode statusCode, String statusText, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, body, responseCharset);
    }

    public PhoneNumberParsingException(HttpStatusCode statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, headers, body, responseCharset);
    }

    public PhoneNumberParsingException(String message, HttpStatusCode statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
        super(message, statusCode, statusText, headers, body, responseCharset);
    }

}
