package com.test.friends.context.user.domain.request.delete;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.exception.UserNotExistException;
import com.test.friends.context.user.domain.find.UserFinder;
import com.test.friends.context.user.domain.request.UserRequestRepository;
import com.test.friends.context.user.domain.request.exception.UserRequestNotExistsException;
import com.test.friends.context.user.domain.request.find.UserRequestFinder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserRequestDeleter {

  private UserFinder userFinder;
  private UserRequestFinder requestFinder;
  private UserRequestRepository requestRepository;

  @Inject
  public UserRequestDeleter(
      UserFinder userFinder,
      UserRequestFinder requestFinder,
      UserRequestRepository requestRepository) {
    this.userFinder = userFinder;
    this.requestFinder = requestFinder;
    this.requestRepository = requestRepository;
  }

  public void delete(UserUsername usernameFrom, UserUsername usernameTo) {
    User fromUser = userFinder.find(usernameFrom);
    guardExist(fromUser, usernameFrom);

    User toUser = userFinder.find(usernameTo);
    guardExist(toUser, usernameFrom);

    guardExistingRequest(usernameFrom, usernameTo);

    requestRepository.delete(usernameTo, usernameFrom);
  }

  private void guardExist(User user, UserUsername username) {
    if (user == null) {
      throw new UserNotExistException(username);
    }
  }

  private void guardExistingRequest(UserUsername usernameFrom, UserUsername usernameTo) {
    boolean existRequest = requestFinder.exists(usernameTo, usernameFrom);
    if (!existRequest) {
      throw new UserRequestNotExistsException(usernameTo, usernameFrom);
    }
  }
}
