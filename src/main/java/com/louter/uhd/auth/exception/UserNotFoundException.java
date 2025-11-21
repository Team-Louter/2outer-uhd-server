package com.louter.uhd.auth.exception;

import com.louter.uhd.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
