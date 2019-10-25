package com.test.friends.context.user.domain.friendship;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.exception.UserNotExistException;
import com.test.friends.context.user.domain.find.UserFinder;
import com.test.friends.context.user.domain.helper.stub.UserStub;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import com.test.friends.context.user.domain.request.delete.UserRequestDeleter;
import com.test.friends.context.user.domain.request.exception.UserRequestNotExistsException;
import com.test.friends.context.user.domain.request.find.UserRequestFinder;
import com.test.friends.context.user.domain.helper.matcher.MatcherException;
import com.test.friends.context.user.domain.helper.mockito.MockitoVerificationMode;
import org.junit.Before;

import java.util.Collections;

import static com.test.friends.context.user.domain.helper.matcher.Similar.similarTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class UserFriendshipUnitTest {

  protected final UserFinder userFinder = mock(UserFinder.class);
  protected final UserFriendshipRepository repository = mock(UserFriendshipRepository.class);
  protected final UserRequestDeleter requestDeleter = mock(UserRequestDeleter.class);
  protected final UserRequestFinder requestFinder = mock(UserRequestFinder.class);

  protected UserUsername fromUsername;
  protected UserUsername toUsername;

  protected User fromUser;
  protected User toUser;

  @Before
  public void init() {
    fromUsername = UserUsernameStub.random();
    toUsername = UserUsernameStub.random(fromUsername.value());

    fromUser = UserStub.with(fromUsername);
    toUser = UserStub.with(toUsername);
  }

  protected void givenFind(User existentUser) {
    given(userFinder.find(existentUser.username())).willReturn(existentUser);
  }

  protected void givenNotFind(UserUsername username) {
    given(userFinder.find(username)).willReturn(null);
  }

  protected void givenFriendship(UserUsername fromUser, UserUsername toUser) {
    given(repository.exists(similarTo(fromUser), similarTo(toUser))).willReturn(true);
  }

  protected void givenNotFriendship(UserUsername fromUser, UserUsername toUser) {
    given(repository.exists(similarTo(fromUser), similarTo(toUser))).willReturn(false);
  }

  protected void givenListFriends(UserUsername fromUser, UserUsername toUser) {
    given(repository.findAll(fromUser)).willReturn(Collections.singletonList(toUser));
  }

  protected void givenRequest(UserUsername fromUser, UserUsername toUser) {
    given(requestFinder.exists(fromUser, toUser)).willReturn(true);
  }

  protected void givenNotRequest(UserUsername fromUser, UserUsername toUser) {
    given(requestFinder.exists(fromUser, toUser)).willReturn(false);
  }

  protected void shouldDeleteRequest(UserUsername fromUser, UserUsername toUser) {
    then(requestDeleter).should(MockitoVerificationMode.once()).delete(fromUser, toUser);
  }

  protected void shouldSave(UserUsername fromUser, UserUsername toUser) {
    then(repository).should(MockitoVerificationMode.once()).add(fromUser, toUser);
  }

  protected void shouldNeverSaveAnything() {
    then(repository).should(never()).add(any(), any());
  }

  protected void shouldListFriends(UserUsername fromUser) {
    then(repository).should(MockitoVerificationMode.once()).findAll(fromUser);
  }

  protected void shouldThrowUserNotExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserNotExistException.class);
  }

  protected void shouldThrowUserRequestNotExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserRequestNotExistsException.class);
  }
}
