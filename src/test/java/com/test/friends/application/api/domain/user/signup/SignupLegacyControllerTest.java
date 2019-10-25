package com.test.friends.application.api.domain.user.signup;

import com.test.friends.application.api.domain.user.ApiTestUtils;
import com.test.friends.context.user.domain.create.UserCreator;
import com.test.friends.context.user.domain.exception.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignupLegacyControllerTest extends ApiTestUtils {

  private static final String PATH_SIGN_UP = "/signup";

  @MockBean private UserCreator userCreator;

  @Before
  public void init() {
    doNothing().when(userCreator).create(fromUsername, fromPassword);
  }

  @Test
  public void GivenValidUserCredentials_WhenCallSignUp_ThenReturnsOk() throws Exception {
    mockMvc
        .perform(
            post(PATH_SIGN_UP)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME, fromUsername.value()))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void GivenInvalidUserUsername_WhenCallSignUp_ThenReturnsBadRequest() throws Exception {
    mockMvc
        .perform(
            post(PATH_SIGN_UP)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME, invalidUsername))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenInvalidUserPassword_WhenCallSignUp_ThenReturnsBadRequest() throws Exception {
    mockMvc
        .perform(
            post(PATH_SIGN_UP)
                .header(X_PASS, invalidPassword)
                .param(USERNAME, fromUsername.value()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenInvalidUserAlreadyExists_WhenCallSignUp_ThenReturnsBadRequest()
      throws Exception {

    Mockito.doThrow(UserAlreadyExistsException.class).when(userCreator).create(fromUsername, fromPassword);

    mockMvc
        .perform(
            post(PATH_SIGN_UP)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME, fromUsername.value()))
        .andExpect(status().isBadRequest());
  }
}
