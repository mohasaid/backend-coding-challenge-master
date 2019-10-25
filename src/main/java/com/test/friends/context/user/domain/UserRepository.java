package com.test.friends.context.user.domain;

public interface UserRepository {

  User find(UserUsername userUsername);

  void save(User user);

}
