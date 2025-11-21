package com.louter.uhd.common.exception;

public class AlreadyUsingException extends AuthenticationException {
    public AlreadyUsingException(String message) {
        super(message);
    }
}
