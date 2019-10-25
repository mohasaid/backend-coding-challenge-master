package com.test.friends.context.user.infrastructure;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserRepository;
import com.test.friends.context.user.domain.UserUsername;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryInMemory implements UserRepository {

  private final Map<UserUsername, User> repository = new ConcurrentHashMap<>();

  @Override
  public User find(UserUsername userUsername) {
    return repository.get(userUsername);
  }

  @Override
  public void save(User user) {
    repository.put(user.username(), user);
  }
}
