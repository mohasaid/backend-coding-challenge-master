package com.test.friends.context.user.domain.exception;

import com.test.friends.context.user.domain.UserUsername;

public class UserInvalidCredentialsException extends RuntimeException {

  public UserInvalidCredentialsException(String username) {
    super(String.format("Invalid credentials for user <%s>", username));
  }

  public UserInvalidCredentialsException(UserUsername username) {
    this(username.value());
  }
}
