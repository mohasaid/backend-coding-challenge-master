package com.test.friends.context.user.domain.create;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserPassword;
import com.test.friends.context.user.domain.UserUnitTest;
import com.test.friends.context.user.domain.helper.stub.UserPasswordStub;
import com.test.friends.context.user.domain.helper.stub.UserStub;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import org.junit.Before;
import org.junit.Test;

public class UserCreatorTest extends UserUnitTest {

  private final UserCreator creator = new UserCreator(finder, repository);

  private UserPassword password;

  @Before
  public void init() {
    username = UserUsernameStub.random();
    password = UserPasswordStub.random();
  }

  @Test
  public void GivenUserRegistrationRequestDoesNotExist_WhenCreatingIt_ThenRegistersIt() {
    User expected = UserStub.created(username, password);

    givenNotFind(username);

    creator.create(username, password);

    shouldSave(expected);
  }

  @Test
  public void GivenUserRegistrationRequestDoesExist_WhenCreatingIt_ThenThrowsException() {
    existentUser = UserStub.with(username);

    givenFind(existentUser);

    shouldThrowUserAlreadyExists(() -> creator.create(username, password));

    shouldNeverSaveAnything();
  }
}
