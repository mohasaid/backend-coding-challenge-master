package com.test.friends.application.api.domain.user.friendship;

import com.test.friends.application.api.domain.user.ApiTestUtils;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.friendship.create.UserFriendshipCreator;
import com.test.friends.context.user.domain.friendship.find.UserFriendshipFinder;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import com.test.friends.context.user.domain.request.create.UserRequestCreator;
import com.test.friends.context.user.domain.request.delete.UserRequestDeleter;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FriendshipLegacyControllerTest extends ApiTestUtils {

  private static final String PATH_FRIENDSHIP = "/friendship/";
  private static final String PATH_ACCEPT = PATH_FRIENDSHIP + "accept";
  private static final String PATH_DECLINE = PATH_FRIENDSHIP + "decline";
  private static final String PATH_REQUEST = PATH_FRIENDSHIP + "request";
  private static final String PATH_LIST = PATH_FRIENDSHIP + "list";

  @MockBean private UserFriendshipFinder friendshipFinder;
  @MockBean private UserFriendshipCreator friendshipCreator;
  @MockBean private UserRequestCreator requestCreator;
  @MockBean private UserRequestDeleter requestDeleter;

  private UserUsername toUsername = UserUsernameStub.random(fromUsername.value());

  @Test
  public void GivenBadPasswordInRequestEndpoint_WhenCallIt_ThenReturnsBadRequest()
      throws Exception {
    mockMvc
        .perform(
            post(PATH_REQUEST)
                .header(X_PASS, invalidPassword)
                .param(USERNAME_FROM, fromUsername.value())
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenBadUsernameInRequestEndpoint_WhenCallIt_ThenReturnsBadRequest()
      throws Exception {
    mockMvc
        .perform(
            post(PATH_REQUEST)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME_FROM, invalidUsername)
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenUserRequest_WhenCallIt_ThenReturnsOk() throws Exception {
    doNothing().when(requestCreator).create(fromUsername, toUsername);

    mockMvc
        .perform(
            post(PATH_REQUEST)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME_FROM, fromUsername.value())
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void GivenBadPasswordInAcceptEndpoint_WhenCallIt_ThenReturnsBadRequest() throws Exception {
    mockMvc
        .perform(
            post(PATH_ACCEPT)
                .header(X_PASS, invalidPassword)
                .param(USERNAME_FROM, fromUsername.value())
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenBadUsernameInAcceptEndpoint_WhenCallIt_ThenReturnsBadRequest() throws Exception {
    mockMvc
        .perform(
            post(PATH_ACCEPT)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME_FROM, invalidUsername)
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenUserAccept_WhenCallIt_ThenReturnsOk() throws Exception {
    doNothing().when(friendshipCreator).create(fromUsername, toUsername);

    mockMvc
        .perform(
            post(PATH_ACCEPT)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME_FROM, fromUsername.value())
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void GivenBadPasswordInDeclineEndpoint_WhenCallIt_ThenReturnsBadRequest()
      throws Exception {
    mockMvc
        .perform(
            post(PATH_DECLINE)
                .header(X_PASS, invalidPassword)
                .param(USERNAME_FROM, fromUsername.value())
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenBadUsernameInDeclineEndpoint_WhenCallIt_ThenReturnsBadRequest()
      throws Exception {
    mockMvc
        .perform(
            post(PATH_DECLINE)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME_FROM, invalidUsername)
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenUserDecline_WhenCallIt_ThenReturnsOk() throws Exception {
    doNothing().when(requestDeleter).delete(fromUsername, toUsername);

    mockMvc
        .perform(
            post(PATH_DECLINE)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME_FROM, fromUsername.value())
                .param(USERNAME_TO, toUsername.value()))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void GivenBadPasswordInListEndpoint_WhenCallIt_ThenReturnsBadRequest() throws Exception {
    mockMvc
        .perform(
            get(PATH_LIST).header(X_PASS, invalidPassword).param(USERNAME, fromUsername.value()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenBadUsernameInListEndpoint_WhenCallIt_ThenReturnsBadRequest() throws Exception {
    mockMvc
        .perform(
            get(PATH_LIST).header(X_PASS, fromPassword.value()).param(USERNAME, invalidUsername))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void GivenUserList_WhenCallIt_ThenReturnsOk() throws Exception {
    List<UserUsername> friends = Arrays.asList(toUsername, toUsername, toUsername);

    when(friendshipFinder.findAllFriends(fromUsername)).thenReturn(friends);

    mockMvc
        .perform(
            get(PATH_LIST)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME, fromUsername.value()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void GivenUserListEmpty_WhenCallIt_ThenReturnsOk() throws Exception {
    when(friendshipFinder.findAllFriends(fromUsername)).thenReturn(new ArrayList<>());

    mockMvc
        .perform(
            get(PATH_LIST)
                .header(X_PASS, fromPassword.value())
                .param(USERNAME, fromUsername.value()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }
}
