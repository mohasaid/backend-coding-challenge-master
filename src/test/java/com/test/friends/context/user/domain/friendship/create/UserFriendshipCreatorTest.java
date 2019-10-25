package com.test.friends.context.user.domain.friendship.create;

import com.test.friends.context.user.domain.friendship.UserFriendshipUnitTest;
import org.junit.Test;

public class UserFriendshipCreatorTest extends UserFriendshipUnitTest {

  private final UserFriendshipCreator friendshipCreator =
      new UserFriendshipCreator(userFinder, repository, requestFinder, requestDeleter);

  @Test
  public void GivenFriendshipCreateRequest_WhenCallCreate_ThenSavesItAndRemovesRequest() {
    givenFind(fromUser);
    givenFind(toUser);

    givenRequest(toUsername, fromUsername);

    friendshipCreator.create(fromUsername, toUsername);

    shouldSave(fromUsername, toUsername);
    shouldSave(toUsername, fromUsername);

    shouldDeleteRequest(fromUsername, toUsername);
  }

  @Test
  public void GivenFriendshipCreateRequestOfUserNotExists_WhenCallCreate_ThenThrowsException() {
    givenNotFind(fromUsername);

    shouldThrowUserNotExists(() -> friendshipCreator.create(fromUsername, toUsername));

    shouldNeverSaveAnything();
  }

  @Test
  public void GivenFriendshipCreateRequestOfRequestNotExists_WhenCallCreate_ThenThrowsException() {
    givenFind(fromUser);
    givenFind(toUser);

    givenNotRequest(fromUsername, toUsername);

    shouldThrowUserRequestNotExists(() -> friendshipCreator.create(fromUsername, toUsername));

    shouldNeverSaveAnything();
  }
}
