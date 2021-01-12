package com.turbal.cnb.exception;

public class ValidationException extends Exception {

    public String message;

    public ValidationException(String message) {
    }

    public String getMessage() {
        return message;
    }
}
