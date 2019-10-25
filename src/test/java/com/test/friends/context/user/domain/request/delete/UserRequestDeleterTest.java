package com.test.friends.context.user.domain.request.delete;

import com.test.friends.context.user.domain.request.UserRequestUnitTest;
import org.junit.Test;

public class UserRequestDeleterTest extends UserRequestUnitTest {

  private final UserRequestDeleter requestDeleter =
      new UserRequestDeleter(userFinder, requestFinder, repository);

  @Test
  public void GivenDeleteRequestWithExistingOne_WhenDeleteIt_ThenItIsDeleted() {
    givenFind(fromUser);
    givenFind(toUser);

    givenRequest(fromUsername, toUsername);

    requestDeleter.delete(toUsername, fromUsername);

    shouldDeleteRequest(fromUsername, toUsername);
  }

  @Test
  public void GivenDeleteRequestNotExistingRequest_WhenDeleteIt_ThenThrowsException() {
    givenFind(fromUser);
    givenFind(toUser);

    givenNotRequest(fromUsername, toUsername);

    shouldThrowUserRequestNotExists(() -> requestDeleter.delete(fromUsername, toUsername));
  }

  @Test
  public void GivenDeleteRequestOfToUserDoesNotExist_WhenDeleteIt_ThenThrowsException() {
    givenFind(fromUser);
    givenNotFind(toUsername);

    givenNotRequest(fromUsername, toUsername);

    shouldThrowUserNotExists(() -> requestDeleter.delete(fromUsername, toUsername));
  }
}
