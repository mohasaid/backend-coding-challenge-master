package com.test.friends.context.user.domain.friendship.find;

import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.friendship.UserFriendshipUnitTest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserFriendshipFinderTest extends UserFriendshipUnitTest {

  private final UserFriendshipFinder friendshipFinder =
      new UserFriendshipFinder(userFinder, repository);

  @Test
  public void GivenExistingFriendship_WhenCheckIfExists_ThenReturnsTrue() {
    givenFind(fromUser);
    givenFind(toUser);

    givenFriendship(fromUsername, toUsername);

    assertTrue(friendshipFinder.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenNonExistingFriendship_WhenCheckIfExists_ThenReturnsFalse() {
    givenFind(fromUser);
    givenFind(toUser);

    givenNotFriendship(fromUsername, toUsername);

    assertFalse(friendshipFinder.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenExistingFriendshipOfToUserDoesNotExist_WhenCheckIfExists_ThenThrowsException() {
    givenFind(fromUser);
    givenNotFind(toUsername);

    givenNotFriendship(fromUsername, toUsername);

    shouldThrowUserNotExists(() -> friendshipFinder.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenAllFriendsRequestOfUserDoesNotExist_WhenCallGetThem_ThenThrowsException() {
    givenNotFind(fromUsername);

    shouldThrowUserNotExists(() -> friendshipFinder.findAllFriends(fromUsername));
  }

  @Test
  public void GivenAllFriendsRequestOfUser_WhenCallGetThem_ThenReturnsAllFriends() {
    givenFind(fromUser);

    givenFriendship(fromUsername, toUsername);

    givenListFriends(fromUsername, toUsername);

    List<UserUsername> friends = friendshipFinder.findAllFriends(fromUsername);

    shouldListFriends(fromUsername);

    assertFalse(friends.isEmpty());
    assertEquals(1, friends.size());
  }
}
