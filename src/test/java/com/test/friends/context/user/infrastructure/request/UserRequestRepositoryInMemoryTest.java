package com.test.friends.context.user.infrastructure.request;

import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import com.test.friends.context.user.domain.request.UserRequestRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserRequestRepositoryInMemoryTest {

  private final UserRequestRepository requestRepository = new UserRequestRepositoryInMemory();

  private UserUsername fromUsername;
  private UserUsername toUsername;
  private UserUsername toUsername2;

  @Before
  public void init() {
    fromUsername = UserUsernameStub.random();
    toUsername = UserUsernameStub.random();
    toUsername2 = UserUsernameStub.random();
  }

  @Test
  public void GivenUserRequestSaveRequest_WhenCallSave_ThenItSavesUser() {
    requestRepository.add(fromUsername, toUsername);
    assertTrue(requestRepository.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenMultipleUserRequestSaveRequest_WhenCallSave_ThenItSavesUser() {
    requestRepository.add(fromUsername, toUsername);
    requestRepository.add(fromUsername, toUsername2);
    requestRepository.add(toUsername, fromUsername);
    requestRepository.add(toUsername, toUsername2);

    assertTrue(requestRepository.exists(fromUsername, toUsername));
    assertTrue(requestRepository.exists(fromUsername, toUsername2));
    assertTrue(requestRepository.exists(toUsername, fromUsername));
    assertTrue(requestRepository.exists(toUsername, toUsername2));
  }

  @Test
  public void GivenUserRequestNonExistingUser_WhenCallExists_ThenItReturnsFalse() {
    assertFalse(requestRepository.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenUserRequestExistingUser_WhenCallExists_ThenItReturnsTrue() {
    requestRepository.add(fromUsername, toUsername);
    assertTrue(requestRepository.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenUserRequestExistingUser_WhenCallDelete_ThenRemovesRequest() {
    requestRepository.add(fromUsername, toUsername);
    assertTrue(requestRepository.exists(fromUsername, toUsername));
    requestRepository.delete(fromUsername, toUsername);
    assertFalse(requestRepository.exists(fromUsername, toUsername));
  }
}
