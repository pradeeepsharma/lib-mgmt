package com.learning.LibraryManagementSystem.exception;

import org.springframework.web.client.RestClientException;

public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(String message) {
        super(message);
    }
}
