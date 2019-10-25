package com.test.friends.application.api.domain.user.signup;

import com.test.friends.application.api.error.InternalServerException;
import com.test.friends.context.user.domain.UserPassword;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.create.UserCreator;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/signup")
public class SignupLegacyController {

  private final UserCreator userCreator;

  @Inject
  public SignupLegacyController(UserCreator userCreator) {
    this.userCreator = userCreator;
  }

  @PostMapping
  void signUp(
      @RequestParam("username") String username, @RequestHeader("X-Password") String password) {
    try {
      UserUsername userUsername = UserUsername.with(username);
      UserPassword userPassword = UserPassword.with(password);

      userCreator.create(userUsername, userPassword);
    } catch (Exception e) {
      throw new InternalServerException("Internal server error", e);
    }
  }
}
