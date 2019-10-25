package com.test.friends.context.user.domain.request.create;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.exception.UserNotExistException;
import com.test.friends.context.user.domain.find.UserFinder;
import com.test.friends.context.user.domain.friendship.exception.UserFriendshipExistsException;
import com.test.friends.context.user.domain.friendship.find.UserFriendshipFinder;
import com.test.friends.context.user.domain.request.UserRequestRepository;
import com.test.friends.context.user.domain.request.exception.UserRequestAlreadyExistException;
import com.test.friends.context.user.domain.request.exception.UserRequestSameUserException;
import com.test.friends.context.user.domain.request.find.UserRequestFinder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserRequestCreator {

  private UserFinder userFinder;
  private UserFriendshipFinder friendshipFinder;
  private UserRequestFinder requestFinder;
  private UserRequestRepository requestRepository;

  @Inject
  public UserRequestCreator(
      UserRequestRepository requestRepository,
      UserFinder userFinder,
      UserFriendshipFinder friendshipFinder,
      UserRequestFinder requestFinder) {
    this.requestRepository = requestRepository;
    this.userFinder = userFinder;
    this.friendshipFinder = friendshipFinder;
    this.requestFinder = requestFinder;
  }

  public void create(UserUsername usernameFrom, UserUsername usernameTo) {
    guard(usernameFrom, usernameTo);

    User fromUser = userFinder.find(usernameFrom);
    guardExist(fromUser, usernameFrom);

    User toUser = userFinder.find(usernameTo);
    guardExist(toUser, usernameTo);

    requestRepository.add(usernameFrom, usernameTo);
  }

  private void guardExist(User user, UserUsername username) {
    if (user == null) {
      throw new UserNotExistException(username);
    }
  }

  private void guard(UserUsername usernameFrom, UserUsername usernameTo) {
    if (usernameFrom.equals(usernameTo)) {
      throw new UserRequestSameUserException(usernameFrom);
    }
    if (requestFinder.exists(usernameFrom, usernameTo)) {
      throw new UserRequestAlreadyExistException(usernameFrom, usernameTo);
    }
    if (friendshipFinder.exists(usernameFrom, usernameTo)) {
      throw new UserFriendshipExistsException(usernameFrom, usernameTo);
    }
  }
}
