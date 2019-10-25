package com.test.friends.context.user.domain.authenticate;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserPassword;
import com.test.friends.context.user.domain.UserUnitTest;
import com.test.friends.context.user.domain.helper.stub.UserPasswordStub;
import com.test.friends.context.user.domain.helper.stub.UserStub;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserAuthenticatorTest extends UserUnitTest {

  private final UserAuthenticator authenticator = new UserAuthenticator(finder);

  private UserPassword password;
  private UserPassword passwordInvalid;

  @Before
  public void init() {
    username = UserUsernameStub.random();
    password = UserPasswordStub.random();
    passwordInvalid = UserPassword.with("anotherPass");
  }

  @Test
  public void GivenUserAuthenticationRequestUserDoesExist_WhenAuthenticatingIt_ThenReturnsTrue() {
    User expected = UserStub.created(username, password);

    givenFind(expected);

    User user = authenticator.authenticate(username, password);

    assertNotNull(user);
  }

  @Test
  public void
      GivenInvalidUserAuthenticationRequestUserDoesExist_WhenAuthenticatingIt_ThenReturnsFalse() {
    User expected = UserStub.created(username, password);

    givenFind(expected);

    shouldThrowUserInvalidCredentials(() -> authenticator.authenticate(username, passwordInvalid));
  }

  @Test
  public void
      GivenInvalidUserAuthenticationRequestUserDoesNotExist_WhenAuthenticatingIt_ThenThrowsException() {
    givenNotFind(username);

    shouldThrowUserNotExists(() -> authenticator.authenticate(username, password));
  }
}
