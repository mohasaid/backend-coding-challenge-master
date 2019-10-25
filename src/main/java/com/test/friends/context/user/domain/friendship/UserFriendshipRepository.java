package com.test.friends.context.user.domain.friendship;

import com.test.friends.context.user.domain.UserUsername;

import java.util.List;

public interface UserFriendshipRepository {

  List<UserUsername> findAll(UserUsername username);

  void add(UserUsername usernameFrom, UserUsername usernameTo);

  boolean exists(UserUsername usernameFrom, UserUsername usernameTo);
}
