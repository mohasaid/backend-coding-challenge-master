package com.test.friends.context.user.domain;

import com.test.friends.context.user.domain.exception.UserAlreadyExistsException;
import com.test.friends.context.user.domain.exception.UserInvalidCredentialsException;
import com.test.friends.context.user.domain.exception.UserNotExistException;
import com.test.friends.context.user.domain.find.UserFinder;
import com.test.friends.context.user.domain.helper.stub.UserStub;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import com.test.friends.context.user.domain.helper.matcher.MatcherException;
import com.test.friends.context.user.domain.helper.mockito.MockitoVerificationMode;
import org.junit.Before;

import static com.test.friends.context.user.domain.helper.matcher.Similar.similarTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class UserUnitTest {

  protected final UserFinder finder = mock(UserFinder.class);
  protected final UserRepository repository = mock(UserRepository.class);

  protected UserUsername username;
  protected User existentUser;

  @Before
  public void init() {
    username = UserUsernameStub.random();
    existentUser = UserStub.with(username);
  }

  protected void givenFind(User existentUser) {
    given(finder.find(similarTo(existentUser.username()))).willReturn(existentUser);
  }

  protected void givenNotFind(UserUsername username) {
    given(finder.find(similarTo(username))).willReturn(null);
  }

  protected void shouldSave(User expected) {
    then(repository).should(MockitoVerificationMode.once()).save(similarTo(expected));
  }

  protected void shouldNeverSaveAnything() {
    then(repository).should(never()).save(any());
  }

  protected void shouldThrowUserAlreadyExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserAlreadyExistsException.class);
  }

  protected void shouldThrowUserNotExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserNotExistException.class);
  }

  protected void shouldThrowUserInvalidCredentials(Runnable runnable) {
    MatcherException.assertThrows(runnable, UserInvalidCredentialsException.class);
  }
}
