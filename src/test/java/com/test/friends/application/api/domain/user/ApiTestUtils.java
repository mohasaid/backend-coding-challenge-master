package com.test.friends.application.api.domain.user;

import com.test.friends.context.user.domain.User;
import com.test.friends.context.user.domain.UserPassword;
import com.test.friends.context.user.domain.UserUsername;
import com.test.friends.context.user.domain.authenticate.UserAuthenticator;
import com.test.friends.context.user.domain.helper.stub.UserPasswordStub;
import com.test.friends.context.user.domain.helper.stub.UserStub;
import com.test.friends.context.user.domain.helper.stub.UserUsernameStub;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ApiTestConfigurer
public abstract class ApiTestUtils {

  @Inject protected MockMvc mockMvc;

  protected static final String USERNAME = "username";
  protected static final String USERNAME_FROM = "usernameFrom";
  protected static final String USERNAME_TO = "usernameTo";
  protected static final String X_PASS = "X-Password";

  protected String invalidUsername = "us";
  protected String invalidPassword = "pass";

  @MockBean private UserAuthenticator userAuthenticator;

  @Before
  public void init() {
    when(userAuthenticator.authenticate(fromUsername, fromPassword)).thenReturn(fromUser);
  }

  protected UserUsername fromUsername = UserUsernameStub.random();
  protected UserPassword fromPassword = UserPasswordStub.random();
  protected User fromUser = UserStub.created(fromUsername, fromPassword);

  protected URI buildUriParams(String path, MultiValueMap<String, String> params) {
    return UriComponentsBuilder.newInstance()
        .path(path)
        .queryParams(params)
        .build()
        .encode()
        .toUri();
  }

  protected <T> HttpEntity<T> bodyParam(T body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return new HttpEntity<>(body, headers);
  }
}
