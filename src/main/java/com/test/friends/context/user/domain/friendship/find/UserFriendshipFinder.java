package com.test.friends.context.user.domain.friendship.find;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.exception.UserNotExistException;
import com.test.friends.context.user.domain.find.UserFinder;
import com.test.friends.context.user.domain.friendship.UserFriendshipRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserFriendshipFinder {

  private UserFinder userFinder;
  private UserFriendshipRepository friendshipRepository;

  @Inject
  public UserFriendshipFinder(
      UserFinder userFinder, UserFriendshipRepository friendshipRepository) {
    this.userFinder = userFinder;
    this.friendshipRepository = friendshipRepository;
  }

  public List<UserUsername> findAllFriends(UserUsername username) {
    User user = userFinder.find(username);
    guard(user, username);

    return friendshipRepository.findAll(username);
  }

  public boolean exists(UserUsername usernameFrom, UserUsername usernameTo) {
    User fromUser = userFinder.find(usernameFrom);
    guard(fromUser, usernameFrom);

    User toUser = userFinder.find(usernameTo);
    guard(toUser, usernameTo);

    return friendshipRepository.exists(usernameFrom, usernameTo);
  }

  private void guard(User user, UserUsername username) {
    if (user == null) {
      throw new UserNotExistException(username);
    }
  }
}
