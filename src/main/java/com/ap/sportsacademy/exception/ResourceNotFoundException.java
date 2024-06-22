package com.ap.sportsacademy.exception;

public class ResourceNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1509637001323644965L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}