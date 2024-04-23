package com.example.kitchendesign.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class MessagingException extends HttpClientErrorException {
    public MessagingException(HttpStatusCode statusCode) {
        super(statusCode);
    }

    public MessagingException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
