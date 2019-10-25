package com.test.friends.context.user.domain.exception;

public class UserPasswordException extends RuntimeException {

  private UserPasswordException(String message) {
    super(message);
  }

  public static UserPasswordException notValid(String password) {
    return new UserPasswordException(String.format("The password <%s> is not valid", password));
  }
}
