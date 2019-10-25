package com.test.friends.context.user.infrastructure.friendship;

import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.friendship.UserFriendshipRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserFriendshipRepositoryInMemory implements UserFriendshipRepository {

  private final Map<UserUsername, List<UserUsername>> repository = new ConcurrentHashMap<>();

  @Override
  public List<UserUsername> findAll(UserUsername username) {
    return repository.get(username) == null ? new LinkedList<>() : repository.get(username);
  }

  @Override
  public void add(UserUsername usernameFrom, UserUsername usernameTo) {
    if (repository.get(usernameFrom) == null) {
      List<UserUsername> friends = new LinkedList<>();
      friends.add(usernameTo);
      repository.put(usernameFrom, friends);
    } else {
      List<UserUsername> friends = repository.get(usernameFrom);
      friends.add(usernameTo);
      repository.put(usernameFrom, friends);
    }
  }

  @Override
  public boolean exists(UserUsername usernameFrom, UserUsername usernameTo) {
    List<UserUsername> friends = repository.get(usernameFrom);
    if (friends != null) {
      return friends.stream().anyMatch(username -> username.equals(usernameTo));
    }
    return false;
  }
}
