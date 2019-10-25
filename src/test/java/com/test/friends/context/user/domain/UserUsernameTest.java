package com.test.friends.context.user.domain;

import com.test.friends.context.user.domain.exception.UsernameException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserUsernameTest {

  @Test
  public void GivenUserWithValidUsername_WhenCreatingIt_ThenReturnsExpectedUsername() {
    String username = "username";

    UserUsername userUsername = UserUsername.with(username);

    assertNotNull(userUsername);
    assertEquals(username, userUsername.value());
  }

  @Test(expected = UsernameException.class)
  public void GivenUserWithInvalidUsernameNull_WhenCreatingIt_ThenThrowsException() {
    String username = null;
    UserUsername.with(username);
  }

  @Test(expected = UsernameException.class)
  public void GivenUserWithInvalidUsernameNotAlphanumeric_WhenCreatingIt_ThenThrowsException() {
    String username = "$_username";
    UserUsername.with(username);
  }

  @Test(expected = UsernameException.class)
  public void GivenUserWithInvalidUsernameLessCharacters_WhenCreatingIt_ThenThrowsException() {
    String username = "abc";
    UserUsername.with(username);
  }

  @Test(expected = UsernameException.class)
  public void GivenUserWithInvalidUsernameMoreCharacters_WhenCreatingIt_ThenThrowsException() {
    String username = "abcdefghijklmnopqrst";
    UserUsername.with(username);
  }
}
