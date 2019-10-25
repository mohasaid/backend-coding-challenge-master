package com.test.friends.context.user.domain.helper.stub;

import com.test.friends.context.user.domain.UserUsername;

import java.util.Random;

public final class UserUsernameStub {

  private static final String[] users = {"user1", "moha2", "pepe3", "juan4", "fran5"};

  public static UserUsername random() {
    return new UserUsername(users[new Random(System.nanoTime()).nextInt(users.length)]);
  }

  public static UserUsername random(String username) {
    String randomUsername = users[new Random(System.nanoTime()).nextInt(users.length)];
    while (randomUsername.equals(username)) {
      randomUsername = users[new Random(System.nanoTime()).nextInt(users.length)];
    }
    return new UserUsername(randomUsername);
  }
}
