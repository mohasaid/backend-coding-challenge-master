package com.test.friends.context.user.infrastructure.friendship;

import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.friendship.UserFriendshipRepository;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserFriendshipRepositoryInMemoryTest {

  private final UserFriendshipRepository friendshipRepository =
      new UserFriendshipRepositoryInMemory();

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
  public void GivenUserFriendshipSaveRequest_WhenCallSave_ThenItSavesFriendship() {
    friendshipRepository.add(fromUsername, toUsername);
    assertTrue(friendshipRepository.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenMultipleUserFriendshipSaveRequest_WhenCallSave_ThenItSavesUser() {
    friendshipRepository.add(fromUsername, toUsername);
    friendshipRepository.add(fromUsername, toUsername2);
    friendshipRepository.add(toUsername, fromUsername);
    friendshipRepository.add(toUsername, toUsername2);

    assertTrue(friendshipRepository.exists(fromUsername, toUsername));
    assertTrue(friendshipRepository.exists(fromUsername, toUsername2));
    assertTrue(friendshipRepository.exists(toUsername, fromUsername));
    assertTrue(friendshipRepository.exists(toUsername, toUsername2));
  }

  @Test
  public void GivenUserFriendshipNonExistingUser_WhenCallExists_ThenItReturnsFalse() {
    assertFalse(friendshipRepository.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenUserFriendshipExistingUser_WhenCallExists_ThenItReturnsTrue() {
    friendshipRepository.add(fromUsername, toUsername);
    assertTrue(friendshipRepository.exists(fromUsername, toUsername));
  }

  @Test
  public void GivenUserFriendshipAllFriendsRequest_WhenCallFindAll_ThenReturnsAllFriends() {
    friendshipRepository.add(fromUsername, toUsername);
    friendshipRepository.add(fromUsername, toUsername2);

    assertTrue(friendshipRepository.exists(fromUsername, toUsername));
    assertTrue(friendshipRepository.exists(fromUsername, toUsername2));

    List<UserUsername> friends = friendshipRepository.findAll(fromUsername);
    assertNotNull(friends);
    assertFalse(friends.isEmpty());
    assertEquals(2, friends.size());
  }

  @Test
  public void GivenUserFriendshipAllFriendsRequest_WhenCallFindAll_ThenReturnsEmptyList() {
    List<UserUsername> friends = friendshipRepository.findAll(fromUsername);
    assertNotNull(friends);
    assertTrue(friends.isEmpty());
  }
}
