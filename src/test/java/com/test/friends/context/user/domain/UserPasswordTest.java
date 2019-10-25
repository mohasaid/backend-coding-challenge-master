package com.test.friends.context.user.domain;

import com.test.friends.context.user.domain.exception.UserPasswordException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserPasswordTest {

  @Test
  public void GivenValidPassword_WhenCreatingIt_ThenReturnsExpectedUsername() {
    String password = "password";

    UserPassword userPassword = UserPassword.with(password);

    assertNotNull(userPassword);
    assertEquals(password, userPassword.value());
  }

  @Test(expected = UserPasswordException.class)
  public void GivenInvalidPasswordNull_WhenCreatingIt_ThenThrowsException() {
    String password = null;
    UserPassword.with(password);
  }

  @Test(expected = UserPasswordException.class)
  public void GivenInvalidPasswordNotAlphanumeric_WhenCreatingIt_ThenThrowsException() {
    String password = "$_password";
    UserPassword.with(password);
  }

  @Test(expected = UserPasswordException.class)
  public void GivenInvalidPasswordLessCharacters_WhenCreatingIt_ThenThrowsException() {
    String password = "abc";
    UserPassword.with(password);
  }

  @Test(expected = UserPasswordException.class)
  public void GivenInvalidPasswordMoreCharacters_WhenCreatingIt_ThenThrowsException() {
    String password = "abcdefghijklmnopqrst";
    UserPassword.with(password);
  }
}
