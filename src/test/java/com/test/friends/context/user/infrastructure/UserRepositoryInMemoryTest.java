package com.test.friends.context.user.infrastructure;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserRepository;
import com.test.friends.context.user.domain.helper.stub.UserStub;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserRepositoryInMemoryTest {

  private final UserRepository userRepository = new UserRepositoryInMemory();

  private User existentUser;

  @Before
  public void init() {
    existentUser = UserStub.with(UserUsernameStub.random());
  }

  @Test
  public void GivenUserSaveRequest_WhenCallSave_ThenItSavesUser() {
    userRepository.save(existentUser);

    User userInMemory = userRepository.find(existentUser.username());

    assertNotNull(userInMemory);
    assertEquals(existentUser, userInMemory);
  }

  @Test
  public void GivenUserFindRequestNonExistingUser_WhenCallSave_ThenItReturnsNullUser() {
    User userInMemory = userRepository.find(existentUser.username());

    assertNull(userInMemory);
  }

  @Test
  public void GivenUserFindRequestExistingUser_WhenCallSave_ThenItReturnsUser() {
    userRepository.save(existentUser);

    User userInMemory = userRepository.find(existentUser.username());

    assertNotNull(userInMemory);
    assertEquals(existentUser, userInMemory);
  }
}
