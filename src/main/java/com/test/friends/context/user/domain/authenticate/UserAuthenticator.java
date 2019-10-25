package com.test.friends.context.user.domain.authenticate;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserPassword;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.exception.UserInvalidCredentialsException;
import com.test.friends.context.user.domain.exception.UserNotExistException;
import com.test.friends.context.user.domain.find.UserFinder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserAuthenticator {

  private UserFinder finder;

  @Inject
  public UserAuthenticator(UserFinder finder) {
    this.finder = finder;
  }

  public User authenticate(UserUsername username, UserPassword password) {
    User user = finder.find(username);
    guard(user, username);
    if (user.password().equals(password)) {
      return user;
    } else {
      throw new UserInvalidCredentialsException(username);
    }
  }

  private void guard(User user, UserUsername username) {
    if (user == null) {
      throw new UserNotExistException(username);
    }
  }
}
