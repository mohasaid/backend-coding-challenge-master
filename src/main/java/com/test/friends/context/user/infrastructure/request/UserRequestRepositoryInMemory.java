package com.test.friends.context.user.infrastructure.request;

import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.request.UserRequestRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRequestRepositoryInMemory implements UserRequestRepository {

  private final Map<UserUsername, List<UserUsername>> repository = new ConcurrentHashMap<>();

  @Override
  public void add(UserUsername usernameFrom, UserUsername usernameTo) {
    if (repository.get(usernameFrom) == null) {
      List<UserUsername> requests = new LinkedList<>();
      requests.add(usernameTo);
      repository.put(usernameFrom, requests);
    } else {
      List<UserUsername> requests = repository.get(usernameFrom);
      requests.add(usernameTo);
      repository.put(usernameFrom, requests);
    }
  }

  @Override
  public void delete(UserUsername usernameFrom, UserUsername usernameTo) {
    List<UserUsername> friends = repository.get(usernameFrom);
    if (friends != null) {
      friends.remove(usernameTo);
      repository.put(usernameFrom, friends);
    }
  }

  @Override
  public boolean exists(UserUsername usernameFrom, UserUsername usernameTo) {
    List<UserUsername> requests = repository.get(usernameFrom);
    if (requests != null) {
      return requests.stream().anyMatch(username -> username.equals(usernameTo));
    }
    return false;
  }
}
