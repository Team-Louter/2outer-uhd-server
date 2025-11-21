package com.louter.uhd.post.exception;

import com.louter.uhd.common.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
