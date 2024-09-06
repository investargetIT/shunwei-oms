package com.yeebotech.shunweioms.auth.exception;

public class AuthenticationException extends RuntimeException {

    private final String errorDetails;

    public AuthenticationException(String message) {
        super(message);
        this.errorDetails = null;
    }

    public AuthenticationException(String message, String errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
}
