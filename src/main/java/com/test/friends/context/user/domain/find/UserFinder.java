package com.test.friends.context.user.domain.find;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserRepository;
import com.test.friends.context.user.domain.UserUsername;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserFinder {

  private UserRepository userRepository;

  @Inject
  public UserFinder(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User find(UserUsername username) {
    return userRepository.find(username);
  }
}
