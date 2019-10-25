package com.test.friends.context.user.domain;

import com.test.friends.context.user.domain.exception.UserPasswordException;

import java.util.Objects;

public class UserPassword {

  private final String password;

  public UserPassword(String password) {
    guard(password);
    this.password = password;
  }

  public String value() {
    return password;
  }

  public static UserPassword with(String password) {
    return new UserPassword(password);
  }

  private void guard(String password) {
    if (password == null
        || password.length() < 8
        || password.length() > 12
        || !isAlphanumeric(password)) {
      throw UserPasswordException.notValid(password);
    }
  }

  private boolean isAlphanumeric(String password) {
    return password.matches("[A-Za-z0-9]+");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserPassword that = (UserPassword) o;
    return Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(password);
  }
}
