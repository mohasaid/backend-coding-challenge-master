package com.test.friends.context.user.domain.exception;

import com.test.friends.context.user.domain.User;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String username) {
    super(String.format("User <%s> already exists", username));
  }

  public UserAlreadyExistsException(User user) {
    this(user.username().value());
  }
}
