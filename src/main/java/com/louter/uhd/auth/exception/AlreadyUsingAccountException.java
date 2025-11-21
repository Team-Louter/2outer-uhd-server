package com.louter.uhd.auth.exception;

import com.louter.uhd.common.exception.AlreadyUsingException;

public class AlreadyUsingAccountException extends AlreadyUsingException {
    public AlreadyUsingAccountException(String message) {
        super(message);
    }
}
