package com.test.friends.context.user.domain.helper.stub;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserPassword;
import com.test.friends.context.user.domain.UserUsername;

public final class UserStub {

  public static User created(UserUsername username, UserPassword password) {
    return new User(username, password);
  }

  public static User with(UserUsername username) {
    return new User(username, UserPasswordStub.random());
  }
}
