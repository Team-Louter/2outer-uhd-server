package com.louter.uhd.auth.exception;

public class AlreadyUsingAccountException extends AuthenticationException {
    public AlreadyUsingAccountException(String message) {
        super(message);
    }
}
