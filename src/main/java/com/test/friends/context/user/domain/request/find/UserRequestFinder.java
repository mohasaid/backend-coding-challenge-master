package com.test.friends.context.user.domain.request.find;

import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.request.UserRequestRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserRequestFinder {

  private UserRequestRepository requestRepository;

  @Inject
  public UserRequestFinder(UserRequestRepository requestRepository) {
    this.requestRepository = requestRepository;
  }

  public boolean exists(UserUsername usernameFrom, UserUsername usernameTo) {
    return requestRepository.exists(usernameFrom, usernameTo);
  }
}
