package com.test.friends.context.user.domain.find;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserUnitTest;
import com.test.friends.context.user.domain.helper.stub.UserStub;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserFinderTest extends UserUnitTest {

  @Before
  public void init() {
    username = UserUsernameStub.random();
  }

  @Test
  public void GivenExistingUser_WhenFindIt_ThenReturnsExpectedUser() {
    existentUser = UserStub.with(username);

    givenFind(existentUser);

    User userToBeFound = finder.find(username);

    assertNotNull(userToBeFound);
    assertEquals(existentUser, userToBeFound);
  }

  @Test
  public void GivenNonExistingUser_WhenFindIt_ThenReturnsNull() {
    givenNotFind(username);

    User userToBeFound = finder.find(username);

    assertNull(userToBeFound);
  }
}
