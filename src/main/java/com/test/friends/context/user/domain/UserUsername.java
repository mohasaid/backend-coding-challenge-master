package com.test.friends.context.user.domain;

import com.test.friends.context.user.domain.exception.UsernameException;

import java.util.Objects;

public class UserUsername {

  private final String username;

  public UserUsername(String username) {
    guard(username);
    this.username = username;
  }

  public String value() {
    return username;
  }

  public static UserUsername with(String username) {
    return new UserUsername(username);
  }

  private void guard(String username) {
    if (username == null
        || username.length() < 5
        || username.length() > 10
        || !isAlphanumeric(username)) {
      throw UsernameException.notValid(username);
    }
  }

  private boolean isAlphanumeric(String username) {
    return username.matches("[A-Za-z0-9]+");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserUsername that = (UserUsername) o;
    return Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }

  @Override
  public String toString() {
    return username;
  }
}
