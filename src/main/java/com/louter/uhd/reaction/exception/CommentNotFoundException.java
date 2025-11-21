package com.louter.uhd.reaction.exception;

import com.louter.uhd.common.exception.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
  public CommentNotFoundException(String message) {
    super(message);
  }
}
