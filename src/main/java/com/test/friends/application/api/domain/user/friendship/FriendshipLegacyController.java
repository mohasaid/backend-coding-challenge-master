package com.test.friends.application.api.domain.user.friendship;

import com.test.friends.application.api.error.InternalServerException;
import com.test.friends.context.user.domain.UserPassword;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.authenticate.UserAuthenticator;
import com.test.friends.context.user.domain.friendship.create.UserFriendshipCreator;
import com.test.friends.context.user.domain.friendship.find.UserFriendshipFinder;
import com.test.friends.context.user.domain.request.create.UserRequestCreator;
import com.test.friends.context.user.domain.request.delete.UserRequestDeleter;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/friendship")
public class FriendshipLegacyController {

  private final UserAuthenticator userAuthenticator;
  private final UserFriendshipFinder friendshipFinder;
  private final UserFriendshipCreator friendshipCreator;
  private final UserRequestCreator requestCreator;
  private final UserRequestDeleter requestDeleter;

  @Inject
  public FriendshipLegacyController(
      UserAuthenticator userAuthenticator,
      UserFriendshipFinder friendshipFinder,
      UserFriendshipCreator friendshipCreator,
      UserRequestCreator requestCreator,
      UserRequestDeleter requestDeleter) {
    this.userAuthenticator = userAuthenticator;
    this.friendshipFinder = friendshipFinder;
    this.friendshipCreator = friendshipCreator;
    this.requestCreator = requestCreator;
    this.requestDeleter = requestDeleter;
  }

  @PostMapping("/request")
  void requestFriendship(
      @RequestParam("usernameFrom") String usernameFrom,
      @RequestParam("usernameTo") String usernameTo,
      @RequestHeader("X-Password") String password) {
    try {
      UserUsername fromUserUsername = UserUsername.with(usernameFrom);
      UserPassword fromUserPassword = UserPassword.with(password);

      userAuthenticator.authenticate(fromUserUsername, fromUserPassword);

      UserUsername toUserUsername = UserUsername.with(usernameTo);

      requestCreator.create(fromUserUsername, toUserUsername);

    } catch (Exception e) {
      throw new InternalServerException(e.getMessage(), e);
    }
  }

  @PostMapping("/accept")
  void acceptFriendship(
      @RequestParam("usernameFrom") String usernameFrom,
      @RequestParam("usernameTo") String usernameTo,
      @RequestHeader("X-Password") String password) {

    try {
      UserUsername fromUserUsername = UserUsername.with(usernameFrom);
      UserPassword fromUserPassword = UserPassword.with(password);

      userAuthenticator.authenticate(fromUserUsername, fromUserPassword);

      UserUsername toUserUsername = UserUsername.with(usernameTo);

      friendshipCreator.create(fromUserUsername, toUserUsername);

    } catch (Exception e) {
      throw new InternalServerException(e.getMessage(), e);
    }
  }

  @PostMapping("/decline")
  void declineFriendship(
      @RequestParam("usernameFrom") String usernameFrom,
      @RequestParam("usernameTo") String usernameTo,
      @RequestHeader("X-Password") String password) {
    try {
      UserUsername fromUserUsername = UserUsername.with(usernameFrom);
      UserPassword fromUserPassword = UserPassword.with(password);

      userAuthenticator.authenticate(fromUserUsername, fromUserPassword);

      UserUsername toUserUsername = UserUsername.with(usernameTo);

      requestDeleter.delete(fromUserUsername, toUserUsername);

    } catch (Exception e) {
      throw new InternalServerException(e.getMessage(), e);
    }
  }

  @GetMapping("/list")
  Object listFriends(
      @RequestParam("username") String username, @RequestHeader("X-Password") String password) {
    try {
      UserUsername userUsername = UserUsername.with(username);
      UserPassword userPassword = UserPassword.with(password);

      userAuthenticator.authenticate(userUsername, userPassword);

      return FriendshipListResponse.of(friendshipFinder.findAllFriends(userUsername)).friends();

    } catch (Exception e) {
      throw new InternalServerException(e.getMessage(), e);
    }
  }
}
