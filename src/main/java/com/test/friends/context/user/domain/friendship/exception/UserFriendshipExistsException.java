package com.test.friends.context.user.domain.friendship.exception;

import com.test.friends.context.user.domain.UserUsername;

public class UserFriendshipExistsException extends RuntimeException {

    public UserFriendshipExistsException(String usernameFrom, String usernameTo) {
        super(String.format("Friendship between user <%s> and user <%s> already exists", usernameFrom, usernameTo));
    }

    public UserFriendshipExistsException(UserUsername usernameFrom, UserUsername usernameTo) {
        this(usernameFrom.value(), usernameTo.value());
    }
}
