package com.test.friends.context.user.domain.exception;

import com.test.friends.context.user.domain.UserUsername;

public class UserNotExistException extends RuntimeException {

  public UserNotExistException(String username) {
    super(String.format("User <%s> does not exist", username));
  }

  public UserNotExistException(UserUsername username) {
    this(username.value());
  }
}
