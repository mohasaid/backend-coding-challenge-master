package com.test.friends.context.user.domain.create;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserPassword;
import com.test.friends.context.user.domain.UserRepository;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.exception.UserAlreadyExistsException;
import com.test.friends.context.user.domain.find.UserFinder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserCreator {

  private UserFinder finder;
  private UserRepository repository;

  @Inject
  public UserCreator(UserFinder userFinder, UserRepository userRepository) {
    this.finder = userFinder;
    this.repository = userRepository;
  }

  public void create(UserUsername username, UserPassword password) {
    User user = finder.find(username);
    guard(user);
    user = new User(username, password);
    repository.save(user);
  }

  private void guard(User user) {
    if (user != null) {
      throw new UserAlreadyExistsException(user);
    }
  }
}
