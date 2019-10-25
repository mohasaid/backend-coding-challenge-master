package com.test.friends.context.user.domain.exception;

public class UsernameException extends RuntimeException {

  private UsernameException(String message) {
    super(message);
  }

  public static UsernameException notValid(String username) {
    return new UsernameException(String.format("The username <%s> is not valid", username));
  }
}
