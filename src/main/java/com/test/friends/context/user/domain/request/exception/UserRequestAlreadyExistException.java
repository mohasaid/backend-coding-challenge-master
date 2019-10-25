package com.test.friends.context.user.domain.request.exception;

import com.test.friends.context.user.domain.UserUsername;

public class UserRequestAlreadyExistException extends RuntimeException {

  public UserRequestAlreadyExistException(String usernameFrom, String usernameTo) {
    super(String.format("User <%s> already have a request from <%s>", usernameTo, usernameFrom));
  }

  public UserRequestAlreadyExistException(UserUsername usernameFrom, UserUsername usernameTo) {
    this(usernameFrom.value(), usernameTo.value());
  }
}
