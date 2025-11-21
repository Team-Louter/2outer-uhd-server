package com.louter.uhd.post.exception;

import com.louter.uhd.common.exception.AuthenticationException;

public class ForbiddenAccessException extends AuthenticationException {
  public ForbiddenAccessException(String message) {
    super(message);
  }
}