package com.test.friends.context.user.domain.helper.stub;

import com.test.friends.context.user.domain.UserPassword;

import java.util.Random;

public final class UserPasswordStub {

  private static final String[] pass = {"password1", "password2", "password3"};

  public static UserPassword random() {
    return new UserPassword(pass[new Random(System.nanoTime()).nextInt(pass.length)]);
  }
}
