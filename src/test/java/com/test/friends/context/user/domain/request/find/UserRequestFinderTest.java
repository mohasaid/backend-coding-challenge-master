package com.test.friends.context.user.domain.request.find;

import com.test.friends.context.user.domain.request.UserRequestUnitTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserRequestFinderTest extends UserRequestUnitTest {

  private final UserRequestFinder requestFinder = new UserRequestFinder(repository);

  @Test
  public void GivenExistingRequest_WhenCheckIfExists_ThenReturnsTrue() {
    givenFind(fromUser);
    givenFind(toUser);

    givenRequestRepository(fromUsername, toUsername);

    assertTrue(requestFinder.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenNotExistingRequest_WhenCheckIfExists_ThenReturnsTrue() {
    givenFind(fromUser);
    givenFind(toUser);

    givenNotRequestRepository(fromUsername, toUsername);

    assertFalse(requestFinder.exists(fromUsername, toUsername));
  }
}
