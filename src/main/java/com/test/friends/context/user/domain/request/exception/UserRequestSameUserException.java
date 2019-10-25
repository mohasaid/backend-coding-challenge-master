package com.test.friends.context.user.domain.request.exception;

import com.test.friends.context.user.domain.UserUsername;

public class UserRequestSameUserException extends RuntimeException {

  public UserRequestSameUserException(String username) {
    super(String.format("User <%s> cannot request friendship to himself", username));
  }

  public UserRequestSameUserException(UserUsername username) {
    this(username.value());
  }
}
