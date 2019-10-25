package com.test.friends.application.api.domain.user.friendship;

import com.test.friends.context.user.domain.UserUsername;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FriendshipListResponse {

  private List<String> friendsUsername;

  public FriendshipListResponse(List<String> friendsUsername) {
    this.friendsUsername = friendsUsername;
  }

  public static FriendshipListResponse of(List<UserUsername> users) {
    List<String> userFriends = users.stream().map(UserUsername::value).collect(Collectors.toList());
    return new FriendshipListResponse(userFriends);
  }

  public List<String> friends() {
    return Collections.unmodifiableList(friendsUsername);
  }

}
