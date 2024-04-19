package com.example.kitchendesign.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

public class NotFoundException extends HttpClientErrorException {
    public NotFoundException(HttpStatusCode statusCode) {
        super(statusCode);
    }

    public NotFoundException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public NotFoundException(HttpStatusCode statusCode, String statusText, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, body, responseCharset);
    }

    public NotFoundException(HttpStatusCode statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, headers, body, responseCharset);
    }

    public NotFoundException(String message, HttpStatusCode statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
        super(message, statusCode, statusText, headers, body, responseCharset);
    }
}
