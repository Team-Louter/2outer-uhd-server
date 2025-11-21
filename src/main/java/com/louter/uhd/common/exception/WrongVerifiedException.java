package com.louter.uhd.common.exception;

public class WrongVerifiedException extends AuthenticationException {
    public WrongVerifiedException(String message) {
        super(message);
    }
}
