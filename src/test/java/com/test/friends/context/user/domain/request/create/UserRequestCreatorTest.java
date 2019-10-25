package com.test.friends.context.user.domain.request.create;

import com.test.friends.context.user.domain.request.UserRequestUnitTest;
import org.junit.Test;

public class UserRequestCreatorTest extends UserRequestUnitTest {

  private final UserRequestCreator requestCreator =
      new UserRequestCreator(repository, userFinder, friendshipFinder, requestFinder);

  @Test
  public void GivenRequestCreator_WhenCallCreate_ThenSavesIt() {
    givenFind(fromUser);
    givenFind(toUser);

    givenNotRequest(fromUsername, toUsername);
    givenNotFriendship(fromUsername, toUsername);

    requestCreator.create(fromUsername, toUsername);

    shouldSave(fromUsername, toUsername);
  }

  @Test
  public void GivenRequestCreatorOfExistingFriendship_WhenCallCreate_ThenThrowsException() {
    givenFind(fromUser);
    givenFind(toUser);

    givenNotRequest(fromUsername, toUsername);
    givenFriendship(fromUsername, toUsername);

    shouldThrowUserFriendshipAlreadyExists(() -> requestCreator.create(fromUsername, toUsername));

    shouldNeverSaveAnything();
  }

  @Test
  public void GivenRequestCreatorOfExistingRequest_WhenCallCreate_ThenThrowsException() {
    givenFind(fromUser);
    givenFind(toUser);

    givenRequest(fromUsername, toUsername);

    shouldThrowUserRequestAlreadyExists(() -> requestCreator.create(fromUsername, toUsername));

    shouldNeverSaveAnything();
  }

  @Test
  public void GivenRequestCreatorOfSameUser_WhenCallCreate_ThenThrowsException() {
    shouldThrowSameUserRequest(() -> requestCreator.create(fromUsername, fromUsername));

    shouldNeverSaveAnything();
  }

  @Test
  public void GivenRequestCreatorOfNonExistingUser_WhenCallCreate_ThenThrowsException() {
    givenNotFind(fromUsername);
    givenFind(toUser);

    shouldThrowUserNotExists(() -> requestCreator.create(fromUsername, toUsername));

    shouldNeverSaveAnything();
  }
}
