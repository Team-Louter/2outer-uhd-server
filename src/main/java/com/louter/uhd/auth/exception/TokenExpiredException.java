package com.louter.uhd.auth.exception;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
