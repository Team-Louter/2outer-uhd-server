package com.louter.uhd.auth.exception;

import com.louter.uhd.common.exception.WrongVerifiedException;

public class WrongVerifiedCodeException extends WrongVerifiedException {
    public WrongVerifiedCodeException(String message) {
        super(message);
    }
}
