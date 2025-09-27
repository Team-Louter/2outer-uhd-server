package com.louter.uhd.auth.exception;

public class WrongVerifiedCodeException extends AuthenticationException {
    public WrongVerifiedCodeException(String message) {
        super(message);
    }
}
