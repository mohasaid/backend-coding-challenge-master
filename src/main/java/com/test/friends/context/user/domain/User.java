package com.test.friends.context.user.domain;

public class User {

  private final UserUsername username;
  private final UserPassword password;

  public User(UserUsername username, UserPassword password) {
    this.username = username;
    this.password = password;
  }

  public UserUsername username() {
    return username;
  }

  public UserPassword password() {
    return password;
  }
}
