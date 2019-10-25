package com.test.friends.context.user.domain.request;

import com.test.friends.context.user.domain.UserUsername;

public interface UserRequestRepository {

  void add(UserUsername usernameFrom, UserUsername usernameTo);

  void delete(UserUsername usernameFrom, UserUsername usernameTo);

  boolean exists(UserUsername usernameFrom, UserUsername usernameTo);
}
