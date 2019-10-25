package com.test.friends.context.user.domain.request;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.exception.UserNotExistException;
import com.test.friends.context.user.domain.find.UserFinder;
import com.test.friends.context.user.domain.friendship.exception.UserFriendshipExistsException;
import com.test.friends.context.user.domain.friendship.find.UserFriendshipFinder;
import com.test.friends.context.user.domain.helper.stub.UserStub;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import com.test.friends.context.user.domain.request.exception.UserRequestAlreadyExistException;
import com.test.friends.context.user.domain.request.exception.UserRequestNotExistsException;
import com.test.friends.context.user.domain.request.exception.UserRequestSameUserException;
import com.test.friends.context.user.domain.request.find.UserRequestFinder;
import com.test.friends.context.user.domain.helper.matcher.MatcherException;
import com.test.friends.context.user.domain.helper.matcher.Similar;
import com.test.friends.context.user.domain.helper.mockito.MockitoVerificationMode;
import org.junit.Before;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class UserRequestUnitTest {

  protected final UserFinder userFinder = mock(UserFinder.class);
  protected final UserRequestFinder requestFinder = mock(UserRequestFinder.class);
  protected final UserFriendshipFinder friendshipFinder = mock(UserFriendshipFinder.class);
  protected final UserRequestRepository repository = mock(UserRequestRepository.class);

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

  protected void givenRequest(UserUsername fromUser, UserUsername toUser) {
    given(requestFinder.exists(fromUser, toUser)).willReturn(true);
  }

  protected void givenRequestRepository(UserUsername fromUser, UserUsername toUser) {
    given(repository.exists(fromUser, toUser)).willReturn(true);
  }

  protected void givenNotRequest(UserUsername fromUser, UserUsername toUser) {
    given(requestFinder.exists(fromUser, toUser)).willReturn(false);
  }

  protected void givenNotRequestRepository(UserUsername fromUser, UserUsername toUser) {
    given(repository.exists(fromUser, toUser)).willReturn(false);
  }

  protected void givenFriendship(UserUsername fromUser, UserUsername toUser) {
    given(friendshipFinder.exists(Similar.similarTo(fromUser), Similar.similarTo(toUser))).willReturn(true);
  }

  protected void givenNotFriendship(UserUsername fromUser, UserUsername toUser) {
    given(friendshipFinder.exists(Similar.similarTo(fromUser), Similar.similarTo(toUser))).willReturn(false);
  }

  protected void shouldDeleteRequest(UserUsername fromUser, UserUsername toUser) {
    then(repository).should(MockitoVerificationMode.once()).delete(fromUser, toUser);
  }

  protected void shouldSave(UserUsername fromUser, UserUsername toUser) {
    then(repository).should(MockitoVerificationMode.once()).add(fromUser, toUser);
  }

  protected void shouldNeverSaveAnything() {
    then(repository).should(never()).add(any(), any());
  }

  protected void shouldThrowUserNotExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserNotExistException.class);
  }

  protected void shouldThrowUserRequestNotExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserRequestNotExistsException.class);
  }

  protected void shouldThrowSameUserRequest(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserRequestSameUserException.class);
  }

  protected void shouldThrowUserRequestAlreadyExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserRequestAlreadyExistException.class);
  }

  protected void shouldThrowUserFriendshipAlreadyExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserFriendshipExistsException.class);
  }
}
