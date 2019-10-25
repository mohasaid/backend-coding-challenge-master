package com.test.friends.context.user.domain.friendship.create;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.exception.UserNotExistException;
import com.test.friends.context.user.domain.find.UserFinder;
import com.test.friends.context.user.domain.friendship.UserFriendshipRepository;
import com.test.friends.context.user.domain.request.delete.UserRequestDeleter;
import com.test.friends.context.user.domain.request.exception.UserRequestNotExistsException;
import com.test.friends.context.user.domain.request.find.UserRequestFinder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserFriendshipCreator {

  private UserFinder userFinder;
  private UserFriendshipRepository friendshipRepository;
  private UserRequestFinder userRequestFinder;
  private UserRequestDeleter userRequestDeleter;

  @Inject
  public UserFriendshipCreator(
      UserFinder userFinder,
      UserFriendshipRepository friendshipRepository,
      UserRequestFinder userRequestFinder,
      UserRequestDeleter userRequestDeleter) {
    this.userFinder = userFinder;
    this.friendshipRepository = friendshipRepository;
    this.userRequestFinder = userRequestFinder;
    this.userRequestDeleter = userRequestDeleter;
  }

  public void create(UserUsername usernameFrom, UserUsername usernameTo) {
    User fromUser = userFinder.find(usernameFrom);
    guard(fromUser, usernameFrom);

    User toUser = userFinder.find(usernameTo);
    guard(toUser, usernameFrom);

    guardExistingRequest(usernameFrom, usernameTo);

    friendshipRepository.add(usernameFrom, usernameTo);
    friendshipRepository.add(usernameTo, usernameFrom);

    userRequestDeleter.delete(usernameFrom, usernameTo);
  }

  private void guard(User user, UserUsername username) {
    if (user == null) {
      throw new UserNotExistException(username);
    }
  }

  private void guardExistingRequest(UserUsername usernameFrom, UserUsername usernameTo) {
    boolean existRequest = userRequestFinder.exists(usernameTo, usernameFrom);
    if (!existRequest) {
      throw new UserRequestNotExistsException(usernameTo, usernameFrom);
    }
  }
}
