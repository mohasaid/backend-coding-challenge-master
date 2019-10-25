package com.test.friends.context.user.domain.request.exception;

import com.test.friends.context.user.domain.UserUsername;

public class UserRequestNotExistsException extends RuntimeException {

    public UserRequestNotExistsException(String usernameFrom, String usernameTo) {
        super(String.format("Request from user <%s> to user <%s> does not exist", usernameFrom, usernameTo));
    }

    public UserRequestNotExistsException(UserUsername usernameFrom, UserUsername usernameTo) {
        this(usernameFrom.value(), usernameTo.value());
    }
}
